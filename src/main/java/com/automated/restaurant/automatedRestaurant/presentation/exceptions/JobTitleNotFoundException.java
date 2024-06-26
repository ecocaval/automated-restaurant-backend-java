package com.automated.restaurant.automatedRestaurant.presentation.exceptions;

import com.automated.restaurant.automatedRestaurant.core.messages.ErrorMessages;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.NotFoundException;

import java.util.Map;
import java.util.UUID;

public class JobTitleNotFoundException extends NotFoundException {

    public JobTitleNotFoundException(UUID id) {
        super(ErrorMessages.ERROR_RESTAURANT_JOB_TITLE_NOT_FOUND_BY_ID.getMessage(), Map.of("id", id));
    }
}
