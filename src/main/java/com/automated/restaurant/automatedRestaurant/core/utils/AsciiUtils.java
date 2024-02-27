package com.automated.restaurant.automatedRestaurant.core.utils;

import java.text.Normalizer;

public class AsciiUtils {

    private static String removeAccents(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }

    private static String toLowerCaseAndTrim(String input) {
        return input.trim().toLowerCase();
    }

    private static String removeSpecialCharactersForDocuments(String input) {
        return input.replaceAll("-", "").replaceAll("\\.", "");
    }

    private static String processString(String input, boolean forDocument) {

        if (input == null || input.isBlank()) {
            return input;
        }

        input = removeAccents(input);
        input = toLowerCaseAndTrim(input);
        input = input.replaceAll("-","");

        if (forDocument) {
            input = removeSpecialCharactersForDocuments(input);
        }

        return input;
    }

    public static String cleanString(String input) {
        return processString(input, Boolean.FALSE);
    }

    public static String cleanDocumentString(String input) {
        return processString(input, Boolean.TRUE);
    }
}

