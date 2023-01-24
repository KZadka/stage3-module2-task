package com.mjc.school.repository.annotation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.implementation.NewsModel;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class DeletingAspect {

    private final BaseRepository<NewsModel, Long> repository;

    public DeletingAspect(BaseRepository<NewsModel, Long> repository) {
        this.repository = repository;
    }

    @AfterReturning("@annotation(OnDelete) && args(id)")
    public void deleteAllEntitiesWithId(Long id) {
        List<NewsModel> newsToDelete = repository.readAll()
                .stream()
                .filter(newsModel -> newsModel.getAuthorId().equals(id))
                .toList();
        newsToDelete.forEach(newsModel -> repository.deleteById(newsModel.getId()));
    }
}
