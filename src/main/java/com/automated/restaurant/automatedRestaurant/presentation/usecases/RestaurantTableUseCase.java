package com.automated.restaurant.automatedRestaurant.presentation.usecases;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateTableRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateTableRequest;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.entities.RestaurantTable;

import java.util.List;
import java.util.UUID;

public interface RestaurantTableUseCase {

    List<RestaurantTable> findAll();

    RestaurantTable findById(UUID id);

    List<RestaurantTable> createAll(Restaurant restaurant, List<CreateTableRequest> requests);

    List<RestaurantTable> updateAll(List<UpdateTableRequest> requests, Restaurant restaurant);

    void deleteAll(List<UUID> tableIds);
}
