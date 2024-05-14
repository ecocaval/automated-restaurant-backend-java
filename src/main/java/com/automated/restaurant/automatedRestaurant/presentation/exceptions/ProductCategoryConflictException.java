package com.automated.restaurant.automatedRestaurant.presentation.exceptions;

import com.automated.restaurant.automatedRestaurant.core.messages.ErrorMessages;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.ConflictException;

import java.util.Map;

public class ProductCategoryConflictException extends ConflictException {

    public ProductCategoryConflictException(String name) {
        super(
                ErrorMessages.ERROR_PRODUCT_CATEGORY_CONFLICT_BY_NAME.getMessage(),
                Map.of("name", name)
        );
    }
}
