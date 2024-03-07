package com.automated.restaurant.automatedRestaurant.presentation.exceptions;

import com.automated.restaurant.automatedRestaurant.core.messages.ErrorMessages;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.ConflictException;

import java.util.Map;
import java.util.UUID;

public class ProductConflictException extends ConflictException {

    public ProductConflictException(UUID restaurantId, String name, Long sku, Long servingCapacity) {
        super(
                ErrorMessages.ERROR_RESTAURANT_PRODUCT_CONFLICT.getMessage(),
                Map.of("restaurantId", restaurantId, "name", name, "sku", sku, "servingCapacity", servingCapacity)
        );
    }
}
