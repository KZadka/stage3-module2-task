package com.mjc.school.service.exception;

public class ValidatorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Integer errorCode;

    public ValidatorException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
