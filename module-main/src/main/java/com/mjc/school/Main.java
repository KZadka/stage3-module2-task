package com.mjc.school;

import com.mjc.school.service.exception.ResourceNotFoundException;
import com.mjc.school.service.exception.ValidatorException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        Utils utils = applicationContext.getBean(Utils.class);

        Scanner input = new Scanner(System.in);

        while (true) {
            try {
                utils.menu();
                switch (input.nextLine()) {
                    case "1" -> utils.getAllNews();
                    case "2" -> utils.getNewsById(input);
                    case "3" -> utils.createNews(input);
                    case "4" -> utils.updateNews(input);
                    case "5" -> utils.deleteNews(input);
                    case "0" -> System.exit(0);
                    default -> System.out.println("Wrong input, please try again.");
                }
            } catch (ValidatorException e) {
                System.out.println(e.getMessage() + " Error code: " + e.getErrorCode());
            } catch (ResourceNotFoundException e) {
                System.out.println(e.getMessage() + " Error code: " + e.getErrorCode());
            }
        }
    }
}
