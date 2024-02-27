package com.automated.restaurant.automatedRestaurant.presentation.exceptions;

import com.automated.restaurant.automatedRestaurant.core.messages.ErrorMessages;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.NotFoundException;

import java.util.Map;
import java.util.UUID;

public class RestaurantTableConflictException extends NotFoundException {

    public RestaurantTableConflictException(String identification) {
        super(
                ErrorMessages.ERROR_RESTAURANT_TABLE_CONFLICT_BY_IDENTIFICATION.getMessage(),
                Map.of("identification", identification)
        );
    }
}
