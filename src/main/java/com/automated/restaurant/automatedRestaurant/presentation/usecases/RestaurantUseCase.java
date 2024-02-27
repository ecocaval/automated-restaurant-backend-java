package com.automated.restaurant.automatedRestaurant.presentation.usecases;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateRestaurantRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateRestaurantRequest;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;

import java.util.Optional;
import java.util.UUID;

public interface RestaurantUseCase {

    Restaurant findById(UUID id);

    Restaurant create(CreateRestaurantRequest request);

    Restaurant update(UpdateRestaurantRequest request, Restaurant restaurant);
}
