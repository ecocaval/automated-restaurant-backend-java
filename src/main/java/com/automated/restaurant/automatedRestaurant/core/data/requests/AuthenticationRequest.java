package com.automated.restaurant.automatedRestaurant.core.data.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthenticationRequest {

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
