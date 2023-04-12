package com.example.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;

public class APIExceptionHandler {

    public ResponseEntity<Object> notFoundException(
            NotFoundException exception
    ) {
        APIException apiException = new APIException(
                exception.getMessage(),
                exception,
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

}