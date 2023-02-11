package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.implementation.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.ResourceNotFoundException;
import com.mjc.school.service.validation.ValidateNewsParam;
import com.mjc.school.service.validation.ValidateNumber;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService implements BaseService<NewsDtoRequest, NewsDtoResponse, Long> {

    private static final String NON_EXISTED_ID = "News with that ID does not exist";

    private final BaseRepository<NewsModel, Long> newsRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public NewsService(BaseRepository<NewsModel, Long> newsRepository, ModelMapper modelMapper) {
        this.newsRepository = newsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<NewsDtoResponse> readAll() {
        return newsRepository.readAll().stream()
                .map(newsModel -> modelMapper.map(newsModel, NewsDtoResponse.class))
                .toList();
    }

    @Override
    @ValidateNumber
    public NewsDtoResponse readById(Long id) {
        Optional<NewsModel> newsModel = newsRepository.readById(id);
        if (newsModel.isPresent()) {
            return modelMapper.map(newsModel, NewsDtoResponse.class);
        } else {
            throw new ResourceNotFoundException(2010, NON_EXISTED_ID);
        }
    }

    @Override
    @ValidateNewsParam
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        NewsModel newsModel = modelMapper.map(createRequest, NewsModel.class);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        newsModel.setCreateDate(date);
        newsModel.setLastUpdateDate(date);
        newsRepository.create(newsModel);

        return modelMapper.map(newsModel, NewsDtoResponse.class);
    }

    @Override
    @ValidateNewsParam
    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        if (newsRepository.existById(updateRequest.getId())) {
            NewsModel newsModel = modelMapper.map(updateRequest, NewsModel.class);

            newsModel.setCreateDate(newsRepository.readById(updateRequest.getId()).get().getCreateDate());
            newsModel.setLastUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            newsRepository.update(newsModel);

            return modelMapper.map(newsModel, NewsDtoResponse.class);
        } else {
            throw new ResourceNotFoundException(2010, NON_EXISTED_ID);
        }
    }

    @Override
    @ValidateNumber
    public boolean deleteById(Long id) {
        if (newsRepository.existById(id)) {
            return newsRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(2010, NON_EXISTED_ID);
        }
    }
}
