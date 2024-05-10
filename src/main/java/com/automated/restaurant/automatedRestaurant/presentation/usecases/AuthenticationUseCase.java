package com.automated.restaurant.automatedRestaurant.presentation.usecases;


import com.automated.restaurant.automatedRestaurant.core.data.requests.AuthenticationRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.RefreshTokenAuthenticationRequest;
import com.automated.restaurant.automatedRestaurant.core.data.responses.AuthenticationResponse;
import com.automated.restaurant.automatedRestaurant.presentation.entities.RefreshToken;

import java.util.UUID;

public interface AuthenticationUseCase {

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse authenticateWithRefreshToken(RefreshTokenAuthenticationRequest request);

    RefreshToken findRefreshToken(UUID refreshToken);

}
