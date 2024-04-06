package com.automated.restaurant.automatedRestaurant.presentation.usecases;

import com.automated.restaurant.automatedRestaurant.core.data.enums.ProductOrderStatus;
import com.automated.restaurant.automatedRestaurant.presentation.entities.CustomerOrder;

import java.util.List;
import java.util.UUID;

public interface CustomerOrderUseCase {

    List<CustomerOrder> getAllOrderFromActiveBills(UUID restaurantId, ProductOrderStatus status);
}
