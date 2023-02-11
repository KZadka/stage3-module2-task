package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.annotation.OnDelete;
import com.mjc.school.repository.model.implementation.AuthorModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exception.ResourceNotFoundException;
import com.mjc.school.service.validation.ValidateAuthorParam;
import com.mjc.school.service.validation.ValidateNumber;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService implements BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> {

    private static final String NON_EXISTED_ID = "Author with that ID does not exist";

    private final BaseRepository<AuthorModel, Long> authorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthorService(BaseRepository<AuthorModel, Long> authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AuthorDtoResponse> readAll() {
        return authorRepository.readAll().stream()
                .map(authorModel -> modelMapper.map(authorModel, AuthorDtoResponse.class))
                .toList();
    }

    @Override
    @ValidateNumber
    public AuthorDtoResponse readById(Long id) {
        Optional<AuthorModel> authorModel = authorRepository.readById(id);
        if (authorModel.isPresent()) {
            return modelMapper.map(authorModel, AuthorDtoResponse.class);
        } else {
            throw new ResourceNotFoundException(2010, NON_EXISTED_ID);
        }
    }

    @Override
    @ValidateAuthorParam
    public AuthorDtoResponse create(AuthorDtoRequest createRequest) {
        AuthorModel authorModel = modelMapper.map(createRequest, AuthorModel.class);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        authorModel.setCreateDate(date);
        authorModel.setLastUpdateDate(date);
        authorRepository.create(authorModel);

        return modelMapper.map(authorModel, AuthorDtoResponse.class);
    }

    @Override
    @ValidateAuthorParam
    public AuthorDtoResponse update(AuthorDtoRequest updateRequest) {
        if (authorRepository.existById(updateRequest.getId())) {
            AuthorModel authorModel = modelMapper.map(updateRequest, AuthorModel.class);

            authorModel.setCreateDate(authorRepository.readById(updateRequest.getId()).get().getCreateDate());
            authorModel.setLastUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            authorRepository.update(authorModel);

            return modelMapper.map(authorModel, AuthorDtoResponse.class);
        } else {
            throw new ResourceNotFoundException(2010, NON_EXISTED_ID);
        }
    }

    @Override
    @OnDelete
    @ValidateNumber
    public boolean deleteById(Long id) {
        if (authorRepository.existById(id)) {
            return authorRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(2010, NON_EXISTED_ID);
        }
    }
}
