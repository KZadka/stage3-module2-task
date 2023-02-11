package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.data.DataSource;
import com.mjc.school.repository.model.implementation.NewsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class NewsRepository implements BaseRepository<NewsModel, Long> {

    private final DataSource dataSource;

    @Autowired
    public NewsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<NewsModel> readAll() {
        return dataSource.getNews();
    }

    @Override
    public Optional<NewsModel> readById(Long id) {
        return dataSource.getNews()
                .stream()
                .filter(newsModel -> id.equals(newsModel.getId()))
                .findFirst();
    }

    @Override
    public NewsModel create(NewsModel entity) {
        List<NewsModel> newsModels = dataSource.getNews();
        if (!newsModels.isEmpty()) {
            entity.setId(newsModels.get(newsModels.size() - 1).getId() + 1L);
        } else {
            entity.setId(1L);
        }
        newsModels.add(entity);
        return entity;
    }

    @Override
    public NewsModel update(NewsModel entity) {
        NewsModel newsModel = readById(entity.getId()).get();
        newsModel.setTitle(entity.getTitle());
        newsModel.setContent(entity.getContent());
        newsModel.setLastUpdateDate(LocalDateTime.now());
        newsModel.setAuthorId(entity.getAuthorId());
        return newsModel;
    }

    @Override
    public boolean deleteById(Long id) {
        return readById(id).map(newsModel -> dataSource.getNews().remove(newsModel))
                .orElse(false);
    }

    @Override
    public boolean existById(Long id) {
        return dataSource.getNews()
                .stream()
                .anyMatch(newsModel -> id.equals(newsModel.getId()));
    }
}
