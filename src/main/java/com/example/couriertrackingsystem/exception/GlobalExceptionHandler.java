package com.example.couriertrackingsystem.exception;

import com.example.couriertrackingsystem.model.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorDto> handle(BaseException exception) {
        final ErrorDto ErrorDto = new ErrorDto(exception.getMessage());
        return new ResponseEntity<>(ErrorDto, exception.getHttpStatus());
    }
}
