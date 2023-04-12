package com.example.addressservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIException {

    private String message;

    private Throwable throwable;

    private HttpStatus httpStatus;

    private ZonedDateTime zonedDateTime;
}