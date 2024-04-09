package com.automated.restaurant.automatedRestaurant.core.validations;

import com.automated.restaurant.automatedRestaurant.core.data.enums.RegexValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<Cpf, String> {

    @Override
    public boolean isValid(String cpfField, ConstraintValidatorContext context) {

        if(cpfField == null) {
            return true;
        }

        return RegexValidator.applyRegexValidation(RegexValidator.CPF_REGEX, cpfField);
    }
}