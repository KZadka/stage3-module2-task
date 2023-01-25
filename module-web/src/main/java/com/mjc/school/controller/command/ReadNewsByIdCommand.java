package com.mjc.school.controller.command;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.utils.Utils;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;

import java.util.Scanner;

public class ReadNewsByIdCommand implements Command {

    private final BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController;
    private final Scanner input;
    private final Utils utils = new Utils();

    public ReadNewsByIdCommand(BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController,
                               Scanner input) {
        this.newsController = newsController;
        this.input = input;
    }


    @Override
    public void execute() {
        System.out.println("Enter news id:");
        System.out.println(newsController.readById(utils.userNumber(input)));
    }
}
