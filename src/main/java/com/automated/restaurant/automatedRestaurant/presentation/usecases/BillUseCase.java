package com.automated.restaurant.automatedRestaurant.presentation.usecases;

import com.automated.restaurant.automatedRestaurant.core.data.requests.PlaceCustomerOrdersRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateCustomerOrdersRequest;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Bill;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Customer;
import com.automated.restaurant.automatedRestaurant.presentation.entities.RestaurantTable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BillUseCase {

    Bill findById(UUID billId);

    List<Bill> findAllActiveBillsByRestaurantId(UUID restaurantId);

    Optional<Bill> findByRestaurantTableAndActiveTrue(RestaurantTable restaurantTable);

    Bill bindCustomerToBill(Customer customer, Optional<Bill> optionalBill, RestaurantTable restaurantTable);

    Bill placeOrders(PlaceCustomerOrdersRequest request, UUID billId);

    Bill updateOrders(UpdateCustomerOrdersRequest request, UUID billId);
}
