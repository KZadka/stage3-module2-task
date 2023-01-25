package com.mjc.school.controller.command;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.utils.Utils;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;

import java.util.Scanner;

public class DeleteNewsCommand implements Command {

    private final BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController;
    private final Scanner input;
    private final Utils utils = new Utils();

    public DeleteNewsCommand(BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController,
                             Scanner input) {
        this.newsController = newsController;
        this.input = input;
    }

    @Override
    public void execute() {
        System.out.println("Enter id of news to delete:");
        System.out.println(newsController.deleteById(utils.userNumber(input)));
    }
}
