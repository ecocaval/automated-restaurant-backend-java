package com.automated.restaurant.automatedRestaurant.core.data.enums;

import lombok.Getter;

@Getter
public enum AccountRole {
    ADMIN("ADMIN"), USER("USER");

    private final String role;

    AccountRole(String role) {
        this.role = role;
    }
}
