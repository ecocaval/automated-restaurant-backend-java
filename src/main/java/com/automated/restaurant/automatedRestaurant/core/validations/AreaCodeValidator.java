package com.automated.restaurant.automatedRestaurant.core.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AreaCodeValidator implements ConstraintValidator<AreaCode, String> {

    @Override
    public boolean isValid(String areaCodeField, ConstraintValidatorContext context) {
        if (areaCodeField == null) {
            return true;
        }
        return areaCodeField.matches("\\d{2}");
    }
}
