package com.maybe.maybe.exception;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(UnmodifiedEntityException.class)
    protected ResponseEntity<Object> handleUnmodifiedEntity(
            UnmodifiedEntityException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(BeanCreationException.class)
    protected ResponseEntity<Object> handleEnityCreation(
            BeanCreationException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(),ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(NotEnoughComponentException.class)
    protected ResponseEntity<Object> handleNotEnoughComponentException(
            NotEnoughComponentException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(),ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }


    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
