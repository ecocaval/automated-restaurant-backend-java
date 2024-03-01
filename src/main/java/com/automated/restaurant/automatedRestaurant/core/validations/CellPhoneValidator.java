package com.automated.restaurant.automatedRestaurant.core.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CellPhoneValidator implements ConstraintValidator<CellPhone, String> {

    @Override
    public boolean isValid(String phoneField, ConstraintValidatorContext context) {
        if (phoneField == null) {
            return true;
        }
        return phoneField.matches("\\d{9}");
    }
}