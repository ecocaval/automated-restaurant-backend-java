package com.automated.restaurant.automatedRestaurant.presentation.exceptions;

import com.automated.restaurant.automatedRestaurant.core.messages.ErrorMessages;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.BadRequestException;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.NotFoundException;

import java.util.Map;

public class ImageStoringException extends BadRequestException {

    public ImageStoringException() {
        super(ErrorMessages.ERROR_ON_IMAGE_STORE.getMessage());
    }
}
