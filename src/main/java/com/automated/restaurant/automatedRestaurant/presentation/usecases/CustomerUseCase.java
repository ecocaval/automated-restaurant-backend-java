package com.automated.restaurant.automatedRestaurant.presentation.usecases;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateCustomerRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateCustomerRequest;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Customer;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;

import java.util.UUID;

public interface CustomerUseCase {

    Customer findById(UUID id);

    Customer create(CreateCustomerRequest request, Restaurant restaurant);

    Customer update(UpdateCustomerRequest request, Customer customer);
}
