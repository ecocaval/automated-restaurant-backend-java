package com.automated.restaurant.automatedRestaurant.core.data.enums;

public enum LoginIdentification {
    EMAIL, CPF;

    public static LoginIdentification getFromLogin(String login) {

        if (RegexValidator.applyRegexValidation(RegexValidator.EMAIL_REGEX , login)) {
            return LoginIdentification.EMAIL;
        }

        if (RegexValidator.applyRegexValidation(RegexValidator.CPF_REGEX , login)) {
            return LoginIdentification.CPF;
        }

        return null;
    }
}
