package com.example.couriertrackingsystem.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {

    protected HttpStatus httpStatus;

    public BaseException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
