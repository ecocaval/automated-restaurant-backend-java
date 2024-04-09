package com.automated.restaurant.automatedRestaurant.core.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public boolean isValid(String emailField, ConstraintValidatorContext context) {

        if(emailField == null) {
            return true;
        }

        try {
            InternetAddress internetAddress = new InternetAddress(emailField);
            internetAddress.validate();
            return true;
        } catch (AddressException e) {
            return false;
        }
    }
}