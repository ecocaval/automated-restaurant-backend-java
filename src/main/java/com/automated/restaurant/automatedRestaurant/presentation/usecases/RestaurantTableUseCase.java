package com.automated.restaurant.automatedRestaurant.presentation.usecases;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateRestaurantTableRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.OnTableUpdateRequest;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.entities.RestaurantTable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantTableUseCase {

    List<RestaurantTable> findAll();

    RestaurantTable findById(UUID id);

    List<RestaurantTable> createAll(Restaurant restaurant, List<CreateRestaurantTableRequest> requests);

    void updateStatus(RestaurantTable oldRestaurantTable, OnTableUpdateRequest request);
}
