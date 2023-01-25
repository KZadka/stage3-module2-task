package com.mjc.school.controller.command;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.utils.Utils;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;

import java.util.Scanner;

public class DeleteAuthorCommand implements Command {

    private final BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController;
    private final Scanner input;
    private final Utils utils = new Utils();

    public DeleteAuthorCommand(BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController,
                               Scanner input) {
        this.authorController = authorController;
        this.input = input;
    }

    @Override
    public void execute() {
        System.out.println("Enter id of author to delete:");
        System.out.println(authorController.deleteById(utils.userNumber(input)));
    }
}
