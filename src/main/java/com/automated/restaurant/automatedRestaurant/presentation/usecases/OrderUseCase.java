package com.automated.restaurant.automatedRestaurant.presentation.usecases;

import com.automated.restaurant.automatedRestaurant.core.data.enums.CustomerOrderStatus;
import com.automated.restaurant.automatedRestaurant.presentation.entities.CustomerOrder;

import java.util.List;
import java.util.UUID;

public interface OrderUseCase {

    List<CustomerOrder> findActiveOrdersByRestaurantId(UUID restaurantId, CustomerOrderStatus customerOrderStatus);
}
