package com.automated.restaurant.automatedRestaurant.core.data.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefreshTokenAuthenticationRequest {

    @NotBlank
    private UUID refreshToken;
}
