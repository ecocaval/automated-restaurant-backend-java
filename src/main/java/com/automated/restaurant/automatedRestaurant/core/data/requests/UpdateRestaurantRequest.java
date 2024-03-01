package com.automated.restaurant.automatedRestaurant.core.data.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateRestaurantRequest {

    @NotNull(message = "O id do restaurante não pode ser nulo para realizar o update.")
    private UUID id;

    private String name;
}
