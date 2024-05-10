package com.automated.restaurant.automatedRestaurant.presentation.exceptions;

import com.automated.restaurant.automatedRestaurant.core.messages.ErrorMessages;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.BadRequestException;
import lombok.Getter;

@Getter
public class CollaboratorInvalidLoginException extends BadRequestException {

    public CollaboratorInvalidLoginException() {
        super(ErrorMessages.ERROR_COLLABORATOR_INVALID_LOGIN.getMessage());
    }
}
