package com.automated.restaurant.automatedRestaurant.presentation.exceptions;

import com.automated.restaurant.automatedRestaurant.core.messages.ErrorMessages;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.NotFoundException;

import java.util.Map;

public class CepNotFoundException extends NotFoundException {

    public CepNotFoundException(String zipCode) {
        super(ErrorMessages.ERROR_ADDRESS_NOT_FOUND_FOR_ZIP_CODE.getMessage(), Map.of("zipCode", zipCode));
    }
}
