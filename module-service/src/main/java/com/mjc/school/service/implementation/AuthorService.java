package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.implementation.AuthorModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class AuthorService implements BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> {

    private static final String NON_EXISTED_ID = "Author with that ID does not exist";

    private final BaseRepository<AuthorModel, Long> repository;
    private final ModelMapper modelMapper;

    public AuthorService(BaseRepository<AuthorModel, Long> repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AuthorDtoResponse> readAll() {
        return repository.readAll().stream()
                .map(authorModel -> modelMapper.map(authorModel, AuthorDtoResponse.class))
                .toList();
    }

    @Override
    public AuthorDtoResponse readById(Long id) {
        Optional<AuthorModel> authorModel = repository.readById(id);
        if (authorModel.isPresent()) {
            return modelMapper.map(authorModel, AuthorDtoResponse.class);
        } else {
            throw new ResourceNotFoundException(2010, NON_EXISTED_ID);
        }
    }

    @Override
    public AuthorDtoResponse create(AuthorDtoRequest createRequest) {
        AuthorModel authorModel = modelMapper.map(createRequest, AuthorModel.class);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        authorModel.setCreateDate(date);
        authorModel.setLastUpdateDate(date);
        repository.create(authorModel);

        return modelMapper.map(authorModel, AuthorDtoResponse.class);
    }

    @Override
    public AuthorDtoResponse update(AuthorDtoRequest updateRequest) {
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
        if (repository.existById(id)) {
            return repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(2010, NON_EXISTED_ID);
        }
    }
}