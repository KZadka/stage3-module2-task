package com.mjc.school.controller.command;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.utils.Utils;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;

import java.util.Scanner;

public class ReadAuthorByIdCommand implements Command {

    private final BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController;
    private final Scanner input;
    private final Utils utils = new Utils();

    public ReadAuthorByIdCommand(BaseController<AuthorDtoRequest,AuthorDtoResponse, Long> authorController,
                                 Scanner input) {
        this.authorController = authorController;
        this.input = input;
    }

    @Override
    public void execute() {
        System.out.println("Enter author id:");
        System.out.println(authorController.readById(utils.userNumber(input)));
    }
}
