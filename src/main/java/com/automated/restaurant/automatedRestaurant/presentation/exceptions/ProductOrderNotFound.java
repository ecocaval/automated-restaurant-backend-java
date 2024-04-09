package com.automated.restaurant.automatedRestaurant.presentation.exceptions;

import com.automated.restaurant.automatedRestaurant.core.messages.ErrorMessages;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.NotFoundException;

import java.util.Map;
import java.util.UUID;

public class ProductOrderNotFound extends NotFoundException {

    public ProductOrderNotFound(UUID customerOrderId, UUID productOrderId) {
        super(
                ErrorMessages.ERROR_COMBINATION_OF_CUSTOMER_AND_PRODUCT_ORDER_NOT_FOUND_BY_ID.getMessage(),
                Map.of("customerOrderId", customerOrderId, "productOrderId", productOrderId)
        );
    }
}
