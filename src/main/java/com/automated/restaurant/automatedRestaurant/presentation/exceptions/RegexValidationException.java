package com.automated.restaurant.automatedRestaurant.presentation.exceptions;

import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.BadRequestException;

public class RegexValidationException extends BadRequestException {

    public RegexValidationException(String message) {
        super(message);
    }
}
