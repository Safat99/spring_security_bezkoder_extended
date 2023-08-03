package com.bezkoder.springjwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

// this class will serve for exception handling and multiple exceptions will be handled by its methods
@ControllerAdvice
public class CustomExceptionHandler {

    /* we are telling spring that, this method is responsible for handling ResourceNotFound exception
     *
     *
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        // 1. create payload containing exception details
        HttpStatus notFound = HttpStatus.NOT_FOUND;

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now(ZoneId.of("Z")) // actual utc timestamp
        );
        // 2. return the response entity
        return new ResponseEntity<>(errorResponse, notFound);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")) // actual utc timestamp
        );
        // 2. return the response entity
        return new ResponseEntity<>(errorResponse, badRequest);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage("Validation failed");
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));
        errorResponse.setErrors(errors);
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
