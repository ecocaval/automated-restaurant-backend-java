package com.automated.restaurant.automatedRestaurant.core.data.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateRestaurantProductRequest {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Double price;
}
