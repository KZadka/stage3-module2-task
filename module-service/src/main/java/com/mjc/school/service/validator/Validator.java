package com.mjc.school.service.validator;

import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.exception.ResourceNotFoundException;
import com.mjc.school.service.exception.ValidatorException;

public class Validator {

    private static final Integer TITLE_MIN_LENGTH = 5;
    private static final Integer TITLE_MAX_LENGTH = 30;
    private static final Integer CONTENT_MIN_LENGTH = 5;
    private static final Integer CONTENT_MAX_LENGTH = 255;
    private static final Integer AUTHOR_MAX_ID = 20;

    public void validateTitle(String title) {
        if (title == null) {
            throw new ValidatorException(1000, "Title cannot be null.");
        } else if (title.trim().length() < TITLE_MIN_LENGTH || title.trim().length() > TITLE_MAX_LENGTH) {
            throw new ValidatorException(1010, "Title length should be between 5 and 30 characters.");
        }
    }

    public void validateContent(String content) {
        if (content == null) {
            throw new ValidatorException(1000, "Content cannot be null.");
        } else if (content.trim().length() < CONTENT_MIN_LENGTH || content.trim().length() > CONTENT_MAX_LENGTH) {
            throw new ValidatorException(1010, "Content length should be between 5 and 255 characters.");
        }
    }

    public void validateId(Long id) {
        if (id == null || id < 1L) {
            throw new ValidatorException(2000, "ID cannot be null or negative.");
        }
    }

    public void validateAuthorId(Long id) {
        if (id > AUTHOR_MAX_ID) {
            throw new ResourceNotFoundException(2010, "Author with that ID does not exist.");
        }
    }

    public void validateDto(NewsDtoRequest request) {
        validateId(request.getAuthorId());
        validateAuthorId(request.getAuthorId());
        validateTitle(request.getTitle());
        validateContent(request.getContent());
    }
}
