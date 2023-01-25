package com.mjc.school.controller.command;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;

public class ReadAllAuthorsCommand implements Command {

    private final BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController;

    public ReadAllAuthorsCommand(BaseController<AuthorDtoRequest,AuthorDtoResponse, Long> authorController) {
        this.authorController = authorController;
    }

    @Override
    public void execute() {
        authorController.readAll().forEach(System.out::println);
    }
}
