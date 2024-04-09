package com.automated.restaurant.automatedRestaurant.core.data.requests;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateRestaurantRequest {

    @NotNull
    private String name;

    private CreateCollaboratorRequest collaborator;
}
