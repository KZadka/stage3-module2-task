package com.mjc.school.controller.utils;

import com.mjc.school.service.exception.ValidatorException;

import java.util.Scanner;

public class Utils {

    public long userNumber(Scanner input) {
        try {
            long userNumber = input.nextLong();
            input.nextLine();
            return userNumber;
        } catch (Exception e) {
            input.nextLine();
            throw new ValidatorException(3000, "Input should be number");
        }
    }
}
