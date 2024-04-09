package com.automated.restaurant.automatedRestaurant.core.utils;

import com.automated.restaurant.automatedRestaurant.core.data.enums.RegexValidator;

public class CpfUtils {

    public static boolean stringIsAValidCpf(String cpf) {
        return RegexValidator.applyRegexValidation(RegexValidator.CPF_REGEX, cpf);
    }
}
