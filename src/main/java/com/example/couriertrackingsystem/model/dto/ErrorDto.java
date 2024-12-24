package com.example.couriertrackingsystem.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDto {

    @JsonProperty("message")
    private String message;

    @JsonCreator
    public ErrorDto(@JsonProperty("message") String message) {
        this.message = message;
    }
}
