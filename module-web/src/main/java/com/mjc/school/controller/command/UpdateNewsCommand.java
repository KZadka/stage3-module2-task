package com.mjc.school.controller.command;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.utils.Utils;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;

import java.util.Scanner;

public class UpdateNewsCommand implements Command {

    private final BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController;
    private final Scanner input;
    private final Utils utils = new Utils();

    public UpdateNewsCommand(BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController,
                             Scanner input) {
        this.newsController = newsController;
        this.input = input;
    }

    @Override
    public void execute() {
        boolean isValid = false;
        NewsDtoRequest request = null;

        while (!isValid) {
            try {
                System.out.println("Enter id of news to update:");
                Long newsId = utils.userNumber(input);
                System.out.println("Enter news title:");
                String title = input.nextLine();
                System.out.println("Enter news content:");
                String content = input.nextLine();
                System.out.println("Enter author id:");
                Long authorId = utils.userNumber(input);
                request = new NewsDtoRequest(newsId, title, content, authorId);
                isValid = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(newsController.update(request));
    }
}
