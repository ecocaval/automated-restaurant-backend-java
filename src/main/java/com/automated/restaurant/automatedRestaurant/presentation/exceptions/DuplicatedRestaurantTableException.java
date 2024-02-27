package com.automated.restaurant.automatedRestaurant.presentation.exceptions;

import com.automated.restaurant.automatedRestaurant.core.messages.ErrorMessages;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.ConflictException;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.NotFoundException;

import java.util.Map;

public class DuplicatedRestaurantTableException extends ConflictException {

    public DuplicatedRestaurantTableException(String identification) {
        super(ErrorMessages.ERROR_DUPLICATED_RESTAURANT_TABLE.getMessage(), Map.of("identification", identification));
    }
}
