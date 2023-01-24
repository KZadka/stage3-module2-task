package com.mjc.school.service.validation;

import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.NewsDtoRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidatingAspect {

    private final Validator validator;

    public ValidatingAspect(Validator validator) {
        this.validator = validator;
    }

    @Before("@annotation(ValidateNumber) && args(id)")
    public void checkId(Long id) {
        validator.validateId(id);
    }

    @Before("@annotation(ValidateNewsParam) && args(request)")
    public void checkNewsRequest(NewsDtoRequest request) {
        validator.validateNewsDto(request);
    }

    @Before("@annotation(ValidateAuthorParam) && args(request)")
    public void checkAuthorRequest(AuthorDtoRequest request) {
        validator.validateAuthorName(request.getName());
    }
}