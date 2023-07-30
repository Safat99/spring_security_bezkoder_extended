package com.bezkoder.springjwt.exception;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

// this class will serve for exception handling and multiple exceptions will be handled by its methods
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /* we are telling spring that, this method is responsible for handling ResourceNotFound exception
    *
    *
    */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        // 1. create payload containing exception details
        HttpStatus notFound = HttpStatus.NOT_FOUND;

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z")) // actual utc timestamp
        );
        // 2. return the response entity
        return new ResponseEntity<>(errorResponse, notFound);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z")) // actual utc timestamp
        );
        // 2. return the response entity
        return new ResponseEntity<>(errorResponse, badRequest);
    }

}
