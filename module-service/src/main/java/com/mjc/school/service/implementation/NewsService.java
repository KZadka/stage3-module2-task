package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.implementation.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.ResourceNotFoundException;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.validation.ValidateNewsParam;
import com.mjc.school.service.validation.ValidateNumber;
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

    @Autowired
    public NewsService(BaseRepository<NewsModel, Long> newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public List<NewsDtoResponse> readAll() {
        return newsRepository.readAll().stream()
                .map(NewsMapper.INSTANCE::newsModelToDto)
                .toList();
    }

    @Override
    @ValidateNumber
    public NewsDtoResponse readById(Long id) {
        Optional<NewsModel> newsModel = newsRepository.readById(id);
        if (newsModel.isPresent()) {
            return NewsMapper.INSTANCE.newsModelToDto(newsModel.get());
        } else {
            throw new ResourceNotFoundException(2010, NON_EXISTED_ID);
        }
    }

    @Override
    @ValidateNewsParam
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        NewsModel newsModel = NewsMapper.INSTANCE.newsDtoToModel(createRequest);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        newsModel.setCreateDate(date);
        newsModel.setLastUpdateDate(date);
        newsRepository.create(newsModel);

        return NewsMapper.INSTANCE.newsModelToDto(newsModel);
    }

    @Override
    @ValidateNewsParam
    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        if (newsRepository.existById(updateRequest.getId())) {
            NewsModel newsModel = NewsMapper.INSTANCE.newsDtoToModel(updateRequest);

            newsModel.setCreateDate(newsRepository.readById(updateRequest.getId()).get().getCreateDate());
            newsModel.setLastUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            newsRepository.update(newsModel);

            return NewsMapper.INSTANCE.newsModelToDto(newsModel);
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
