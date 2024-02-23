package com.automated.restaurant.automatedRestaurant.core.data.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BrazilState {

    AC("AC"), AL("AL"), AM("AM"), AP("AP"), BA("BA"), CE("CE"), DF("DF"), ES("ES"), GO("GO"), MA("MA"), MG("MG"),
    MS("MS"), MT("MT"), PA("PA"), PB("PB"), PE("PE"), PI("PI"), PR("PR"), RJ("RJ"), RN("RN"), RO("RO"), RR("RR"),
    RS("RS"), SC("SC"), SE("SE"), SP("SP"), TO("TO");

    private final String acronym;

    BrazilState(String acronym) {
        this.acronym = acronym;
    }

    public static BrazilState getFromString(String stateString) {
        return Arrays.stream(BrazilState.values())
                .filter(state -> state.getAcronym().equalsIgnoreCase(stateString))
                .findFirst()
                .orElse(null);
    }
}
