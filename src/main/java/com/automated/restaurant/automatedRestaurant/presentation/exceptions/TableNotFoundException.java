package com.automated.restaurant.automatedRestaurant.presentation.exceptions;

import com.automated.restaurant.automatedRestaurant.core.messages.ErrorMessages;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.NotFoundException;

import java.util.Map;
import java.util.UUID;

public class TableNotFoundException extends NotFoundException {

    public TableNotFoundException(UUID id) {
        super(ErrorMessages.ERROR_RESTAURANT_TABLE_NOT_FOUND_BY_ID.getMessage(), Map.of("id", id));
    }
}
