package com.mjc.school.controller.command;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;

public class ReadAllNewsCommand implements Command {

    private final BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController;

    public ReadAllNewsCommand(BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController) {
        this.newsController = newsController;
    }


    @Override
    public void execute() {
        newsController.readAll().forEach(System.out::println);
    }
}
