package com.automated.restaurant.automatedRestaurant.presentation.exceptions;

import com.automated.restaurant.automatedRestaurant.core.messages.ErrorMessages;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.NotFoundException;

import java.util.Map;
import java.util.UUID;

public class DuplicatedJobTitleException extends NotFoundException {

    public DuplicatedJobTitleException(String jobTitle) {
        super(ErrorMessages.ERROR_DUPLICATED_JOB_TITLE.getMessage(), Map.of("jobTitle", jobTitle));
    }
}
