package com.mjc.school.controller.command;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.utils.Utils;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;

import java.util.Scanner;

public class UpdateAuthorCommand implements Command {

    private final BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController;
    private final Scanner input;
    private final Utils utils = new Utils();

    public UpdateAuthorCommand(BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController,
                               Scanner input) {
        this.authorController = authorController;
        this.input = input;
    }

    @Override
    public void execute() {
        boolean isValid = false;
        AuthorDtoRequest request = null;

        while (!isValid) {
            try {
                System.out.println("Enter id of author to update:");
                Long authorId = utils.userNumber(input);
                System.out.println("Enter author name:");
                String name = input.nextLine();
                request = new AuthorDtoRequest(authorId, name);
                isValid = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(authorController.update(request));
    }
}
