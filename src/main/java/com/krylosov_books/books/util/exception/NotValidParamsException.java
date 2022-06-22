package com.krylosov_books.books.util.exception;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class NotValidParamsException {

    private final int status;
    private final String message;
    private List<FieldError> fieldErrors = new ArrayList<>();

    NotValidParamsException(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void addFieldError(String object, String path, String message) {
        FieldError error = new FieldError(object, path, message);
        fieldErrors.add(error);
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public static NotValidParamsException processFieldErrors(List<org.springframework.validation.FieldError> fieldErrors) {
        NotValidParamsException error = new NotValidParamsException(BAD_REQUEST.value(), "Validation error!");
        for (org.springframework.validation.FieldError fieldError: fieldErrors) {
            error.addFieldError(fieldError.getObjectName(),fieldError.getField(), fieldError.getDefaultMessage());
        }
        return error;
    }
}
