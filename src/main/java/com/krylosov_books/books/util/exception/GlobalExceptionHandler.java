package com.krylosov_books.books.util.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.List;

import static com.krylosov_books.books.util.exception.NotValidParamsException.processFieldErrors;
import static org.springframework.http.HttpStatus.BAD_REQUEST;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        var errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceWasDeletedException.class)
    protected ResponseEntity<MyGlobalExceptionHandler> handleDeleteException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("This book was deleted!"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    protected ResponseEntity<?> handleExistException(ResourceAlreadyExistsException ex, WebRequest request) {
        var errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public NotValidParamsException methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var result = ex.getBindingResult();
        List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        System.out.println(ex.getClass());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    @Data
    @AllArgsConstructor
    private static class MyGlobalExceptionHandler {
        private String message;
    }
}
