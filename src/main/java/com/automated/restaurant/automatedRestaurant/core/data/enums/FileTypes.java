package com.automated.restaurant.automatedRestaurant.core.data.enums;

import lombok.Getter;

import java.util.List;

@Getter
public enum FileTypes {

    PNG("image/png"), JPEG("image/jpeg");

    private final String type;

    public static final List<String> ALLOWED_IMAGE_TYPES = List.of(PNG.getType(), JPEG.getType());

    FileTypes(String type) {
        this.type = type;
    }
}
