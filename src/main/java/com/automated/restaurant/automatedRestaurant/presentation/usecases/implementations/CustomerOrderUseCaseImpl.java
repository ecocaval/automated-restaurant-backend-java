package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.automated.restaurant.automatedRestaurant.core.data.enums.ProductOrderStatus;
import com.automated.restaurant.automatedRestaurant.presentation.entities.CustomerOrder;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.CustomerOrderRepository;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.CustomerOrderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerOrderUseCaseImpl implements CustomerOrderUseCase {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Override
    public List<CustomerOrder> getAllOrderFromActiveBills(UUID restaurantId, ProductOrderStatus status) {
        return status != null ?
            customerOrderRepository.findAllActiveOrdersByRestaurantIdAndStatus(restaurantId, status) :
            customerOrderRepository.findAllActiveOrdersByRestaurantId(restaurantId);
    }
}
