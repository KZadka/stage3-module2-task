package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.implementation.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class NewsService implements BaseService<NewsDtoRequest, NewsDtoResponse, Long> {

    private final BaseRepository<NewsModel, Long> repository;
    private final ModelMapper modelMapper;

    public NewsService(BaseRepository<NewsModel, Long> repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<NewsDtoResponse> readAll() {
        return repository.readAll().stream()
                .map(newsModel -> modelMapper.map(newsModel, NewsDtoResponse.class))
                .toList();
    }

    @Override
    public NewsDtoResponse readById(Long id) {
        Optional<NewsModel> newsModel = repository.readById(id);
        if (newsModel.isPresent()) {
            return modelMapper.map(newsModel, NewsDtoResponse.class);
        } else {
            throw new ResourceNotFoundException(2010, "News with that ID does not exist");
        }
    }

    @Override
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        NewsModel newsModel = modelMapper.map(createRequest, NewsModel.class);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        newsModel.setCreateDate(date);
        newsModel.setLastUpdateDate(date);
        repository.create(newsModel);
        return modelMapper.map(newsModel, NewsDtoResponse.class);
    }

    @Override
    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        if (repository.existById(updateRequest.getId())) {
            NewsModel newsModel = modelMapper.map(updateRequest, NewsModel.class);
            newsModel.setLastUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            repository.update(newsModel);
            return modelMapper.map(newsModel, NewsDtoResponse.class);
        } else {
            throw new ResourceNotFoundException(2010, "News with that ID does not exist");
        }
    }

    @Override
    public boolean deleteById(Long id) {
        if (repository.existById(id)) {
            return repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(2010, "News with that ID does not exist.");
        }
    }
}
