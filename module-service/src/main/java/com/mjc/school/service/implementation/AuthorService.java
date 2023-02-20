package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.annotation.OnDelete;
import com.mjc.school.repository.model.implementation.AuthorModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exception.ResourceNotFoundException;
import com.mjc.school.service.mapper.AuthorMapper;
import com.mjc.school.service.validation.ValidateAuthorParam;
import com.mjc.school.service.validation.ValidateNumber;
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

    @Autowired
    public AuthorService(BaseRepository<AuthorModel, Long> authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDtoResponse> readAll() {
        return authorRepository.readAll().stream()
                .map(AuthorMapper.INSTANCE::authorModelToDto)
                .toList();
    }

    @Override
    @ValidateNumber
    public AuthorDtoResponse readById(Long id) {
        Optional<AuthorModel> authorModel = authorRepository.readById(id);
        if (authorModel.isPresent()) {
            return AuthorMapper.INSTANCE.authorModelToDto(authorModel.get());
        } else {
            throw new ResourceNotFoundException(2010, NON_EXISTED_ID);
        }
    }

    @Override
    @ValidateAuthorParam
    public AuthorDtoResponse create(AuthorDtoRequest createRequest) {
        AuthorModel authorModel = AuthorMapper.INSTANCE.authorDtoToModel(createRequest);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        authorModel.setCreateDate(date);
        authorModel.setLastUpdateDate(date);
        authorRepository.create(authorModel);

        return AuthorMapper.INSTANCE.authorModelToDto(authorModel);
    }

    @Override
    @ValidateAuthorParam
    public AuthorDtoResponse update(AuthorDtoRequest updateRequest) {
        if (authorRepository.existById(updateRequest.getId())) {
            AuthorModel authorModel = AuthorMapper.INSTANCE.authorDtoToModel(updateRequest);

            authorModel.setCreateDate(authorRepository.readById(updateRequest.getId()).get().getCreateDate());
            authorModel.setLastUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            authorRepository.update(authorModel);

            return AuthorMapper.INSTANCE.authorModelToDto(authorModel);
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
