package com.automated.restaurant.automatedRestaurant.presentation.exceptions.base;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BaseException extends RuntimeException {

    private final String message;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private Object details;

    public BaseException(String message, Object details) {
        this.message = message;
        this.details = details;
    }

    public BaseException(String message) {
        this.message = message;
    }
}
