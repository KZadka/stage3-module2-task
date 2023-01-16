package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.implementation.AuthorModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exception.ResourceNotFoundException;
import com.mjc.school.service.validator.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService implements BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> {

    private static final String NON_EXISTED_ID = "Author with that ID does not exist";

    private final BaseRepository<AuthorModel, Long> repository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    public AuthorService(BaseRepository<AuthorModel, Long> repository, ModelMapper modelMapper,
                         Validator validator) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public List<AuthorDtoResponse> readAll() {
        return repository.readAll().stream()
                .map(authorModel -> modelMapper.map(authorModel, AuthorDtoResponse.class))
                .toList();
    }

    @Override
    public AuthorDtoResponse readById(Long id) {
        validator.validateId(id);
        Optional<AuthorModel> authorModel = repository.readById(id);
        if (authorModel.isPresent()) {
            return modelMapper.map(authorModel, AuthorDtoResponse.class);
        } else {
            throw new ResourceNotFoundException(2010, NON_EXISTED_ID);
        }
    }

    @Override
    public AuthorDtoResponse create(AuthorDtoRequest createRequest) {
        validator.validateAuthorName(createRequest.getName());
        AuthorModel authorModel = modelMapper.map(createRequest, AuthorModel.class);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        authorModel.setCreateDate(date);
        authorModel.setLastUpdateDate(date);
        repository.create(authorModel);

        return modelMapper.map(authorModel, AuthorDtoResponse.class);
    }

    @Override
    public AuthorDtoResponse update(AuthorDtoRequest updateRequest) {
        validator.validateAuthorDto(updateRequest);
        if (repository.existById(updateRequest.getId())) {
            AuthorModel authorModel = modelMapper.map(updateRequest, AuthorModel.class);

            authorModel.setLastUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            repository.update(authorModel);

            return modelMapper.map(authorModel, AuthorDtoResponse.class);
        } else {
            throw new ResourceNotFoundException(2010, NON_EXISTED_ID);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        validator.validateId(id);
        if (repository.existById(id)) {
            return repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(2010, NON_EXISTED_ID);
        }
    }
}
