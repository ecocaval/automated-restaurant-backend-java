package com.automated.restaurant.automatedRestaurant.core.utils;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import java.util.Objects;
import java.util.UUID;

public class WebSocketUtils {

    public static UUID getIdSectionFromUrl(SimpMessageHeaderAccessor headerAccessor, int sectionIndex) {

        String[] splitDestination = Objects.requireNonNull(headerAccessor.getDestination()).split("/");

        if(sectionIndex < 0 || sectionIndex > splitDestination.length - 1) {
            return null;
        }

        try {
            return UUID.fromString(splitDestination[sectionIndex]);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
