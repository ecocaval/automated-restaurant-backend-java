package com.automated.restaurant.automatedRestaurant.core.utils;

import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.BadRequestException;

import java.util.UUID;

public class UuidUtils {

    public static UUID getValidUuidFromString(String uuid, String errorMessage) {

        UUID validatedUuid = null;

        try {
            validatedUuid = UUID.fromString(uuid);

        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(errorMessage);
        }

        return validatedUuid;
    }

    public static UUID getValidUuidFromString(String uuid) {
        return UUID.fromString(uuid);
    }
}
