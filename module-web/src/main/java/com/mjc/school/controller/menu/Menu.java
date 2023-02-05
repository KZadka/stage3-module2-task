package com.mjc.school.controller.menu;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.command.*;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.ResourceNotFoundException;
import com.mjc.school.service.exception.ValidatorException;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Menu {

    private final BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController;
    private final BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController;
    private final Invoker invoker;
    Scanner input = new Scanner(System.in);

    public Menu(BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController,
                BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController,
                Invoker invoker) {
        this.newsController = newsController;
        this.authorController = authorController;
        this.invoker = invoker;
    }

    public void start() {
        boolean running = true;
        while (running) {
            try {
                menuScreen();
                switch (input.nextLine()) {
                    case "1" -> invoker.setCommand(new ReadAllNewsCommand(newsController));
                    case "2" -> invoker.setCommand(new ReadAllAuthorsCommand(authorController));
                    case "3" -> invoker.setCommand(new ReadNewsByIdCommand(newsController, input));
                    case "4" -> invoker.setCommand(new ReadAuthorByIdCommand(authorController, input));
                    case "5" -> invoker.setCommand(new CreateNewsCommand(newsController, input));
                    case "6" -> invoker.setCommand(new CreateAuthorCommand(authorController, input));
                    case "7" -> invoker.setCommand(new UpdateNewsCommand(newsController, input));
                    case "8" -> invoker.setCommand(new UpdateAuthorCommand(authorController, input));
                    case "9" -> invoker.setCommand(new DeleteNewsCommand(newsController, input));
                    case "10" -> invoker.setCommand(new DeleteAuthorCommand(authorController, input));
                    case "0" -> {
                        running = false;
                        System.exit(0);
                    }
                    default -> System.out.println("Wrong input, please try again.");
                }
            } catch (ValidatorException e) {
                System.out.println(e.getMessage() + " Error code: " + e.getErrorCode());
            } catch (ResourceNotFoundException e) {
                System.out.println(e.getMessage() + " Error code: " + e.getErrorCode());
            }
        }
    }

    public void menuScreen() {
        System.out.println("""
                Pick a number for operation:
                1. Get all news
                2. Get all authors
                3. Get news by id
                4. Get author by id
                5. Create news
                6. Create author
                7. Update news by id
                8. Update author by id
                9. Delete news by id
                10. Delete author by id
                0. Exit program
                """);
    }
}
