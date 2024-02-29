package com.automated.restaurant.automatedRestaurant.presentation.exceptions;

import com.automated.restaurant.automatedRestaurant.core.messages.ErrorMessages;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.NotFoundException;

import java.util.Map;

public class DuplicatedProductException extends NotFoundException {

    public DuplicatedProductException(String product) {
        super(ErrorMessages.ERROR_DUPLICATED_PRODUCT.getMessage(), Map.of("product", product));
    }
}
