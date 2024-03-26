package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateRestaurantRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateRestaurantRequest;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.*;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.RestaurantRepository;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.RestaurantTableRepository;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.ImageStoreUseCase;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.RestaurantUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RestaurantUseCaseImpl implements RestaurantUseCase {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    @Autowired
    private ImageStoreUseCase imageStoreUseCase;

    @Override
    public Restaurant findById(UUID id) {
        return this.restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    @Override
    public Restaurant create(CreateRestaurantRequest request) {
        return this.restaurantRepository.save(
                Restaurant.fromCreateRequest(request)
        );
    }

    @Override
    public Restaurant update(UpdateRestaurantRequest request, Restaurant restaurant) {

        updateRestaurantName(request, restaurant);

        return this.restaurantRepository.save(restaurant);
    }

    private void updateRestaurantName(UpdateRestaurantRequest request, Restaurant restaurant) {
        if (request.getName() != null) {
            restaurant.setName(request.getName());
        }
    }
}
