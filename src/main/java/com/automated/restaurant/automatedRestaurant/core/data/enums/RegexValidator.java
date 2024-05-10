package com.automated.restaurant.automatedRestaurant.core.data.enums;

import com.automated.restaurant.automatedRestaurant.presentation.exceptions.RegexValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

@AllArgsConstructor
@Getter
public enum RegexValidator {

    EMAIL_REGEX("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-zA-Z]{2,})$"),
    CPF_REGEX("^[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}$"),
    CNPJ_REGEX("^(\\d{2})\\.?(\\d{3})\\.?(\\d{3})/?\\d{4}-?(\\d{2})$")
    ;

    private final String regex;

    public static void applyRegexValidation(RegexValidator regex, String input, String errorMessage) {
        if (!Pattern.matches(regex.getRegex(), input)) {
            throw new RegexValidationException(errorMessage);
        }
    }

    public static boolean applyRegexValidation(RegexValidator regex, String input){
        return Pattern.matches(regex.getRegex(), input);
    }
}
