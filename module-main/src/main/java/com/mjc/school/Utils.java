package com.mjc.school;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.ValidatorException;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Utils {

    private final BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController;
    private final BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController;

    public Utils(BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController,
                 BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController) {
        this.newsController = newsController;
        this.authorController = authorController;
    }

    public void menu() {
        System.out.println("Pick a number for operation: ");
        System.out.println("1. Get all news");
        System.out.println("2. Get all authors");
        System.out.println("3. Get news by id");
        System.out.println("4. Get author by id");
        System.out.println("5. Create news");
        System.out.println("6. Create author");
        System.out.println("7. Update news by id");
        System.out.println("8. Update author by id");
        System.out.println("9. Delete news by id");
        System.out.println("10. Delete author by id");
        System.out.println("0. Exit program");
    }

    public void getAllNews() {
        newsController.readAll().forEach(System.out::println);
    }

    public void getNewsById(Scanner input) {
        System.out.println("Enter news id:");
        System.out.println(newsController.readById(userNumberValidation(input)));
    }

    public void createNews(Scanner input) {
        boolean isValid = false;
        NewsDtoRequest request = null;

        while (!isValid) {
            try {
                System.out.println("Enter news title:");
                String title = input.nextLine();
                System.out.println("Enter news content:");
                String content = input.nextLine();
                System.out.println("Enter author id:");
                Long authorId = userNumberValidation(input);
                request = new NewsDtoRequest(null, title, content, authorId);
                isValid = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(newsController.create(request));
    }

    public void updateNews(Scanner input) {
        boolean isValid = false;
        NewsDtoRequest request = null;

        while (!isValid) {
            try {
                System.out.println("Enter id of news to update:");
                Long newsId = userNumberValidation(input);
                System.out.println("Enter news title:");
                String title = input.nextLine();
                System.out.println("Enter news content:");
                String content = input.nextLine();
                System.out.println("Enter author id:");
                Long authorId = userNumberValidation(input);
                request = new NewsDtoRequest(newsId, title, content, authorId);
                isValid = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(newsController.update(request));
    }

    public void deleteNews(Scanner input) {
        System.out.println("Enter id of news to delete:");
        System.out.println(newsController.deleteById(userNumberValidation(input)));
    }

    private long userNumberValidation(Scanner input) {
        try {
            long userNumber = input.nextLong();
            input.nextLine();
            return userNumber;
        } catch (Exception e) {
            input.nextLine();
            throw new ValidatorException(3000, "Input should be number");
        }
    }

    public void getAllAuthors() {
        authorController.readAll().forEach(System.out::println);
    }

    public void getAuthorById(Scanner input) {
        System.out.println("Enter author id:");
        System.out.println(authorController.readById(userNumberValidation(input)));
    }

    public void createAuthor(Scanner input) {
        boolean isValid = false;
        AuthorDtoRequest request = null;

        while (!isValid) {
            try {
                System.out.println("Enter author name:");
                String name = input.nextLine();
                request = new AuthorDtoRequest(null, name);
                isValid = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(authorController.create(request));
    }

    public void updateAuthor(Scanner input) {
        boolean isValid = false;
        AuthorDtoRequest request = null;

        while (!isValid) {
            try {
                System.out.println("Enter id of author to update:");
                Long authorId = userNumberValidation(input);
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

    public void deleteAuthor(Scanner input) {
        System.out.println("Enter id of author to delete:");
        System.out.println(authorController.deleteById(userNumberValidation(input)));
    }
}
