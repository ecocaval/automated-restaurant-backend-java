package com.automated.restaurant.automatedRestaurant.core.data.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class AuthenticationResponse {

    private String accessToken;

    private UUID refreshToken;
}
