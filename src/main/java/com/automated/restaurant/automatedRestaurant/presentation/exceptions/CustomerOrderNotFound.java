package com.automated.restaurant.automatedRestaurant.presentation.exceptions;

import com.automated.restaurant.automatedRestaurant.core.messages.ErrorMessages;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.NotFoundException;

import java.util.Map;
import java.util.UUID;

public class CustomerOrderNotFound extends NotFoundException {

    public CustomerOrderNotFound(UUID customerOrderId) {
        super(
                ErrorMessages.ERROR_CUSTOMER_ORDER_NOT_FOUND_BY_ID.getMessage(),
                Map.of("customerOrderId", customerOrderId)
        );
    }
}
