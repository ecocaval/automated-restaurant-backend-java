package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.automated.restaurant.automatedRestaurant.core.data.enums.CustomerOrderStatus;
import com.automated.restaurant.automatedRestaurant.presentation.entities.CustomerOrder;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.CustomerOrderRepository;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.OrderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderUseCaseImpl implements OrderUseCase {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Override
    public List<CustomerOrder> findActiveOrdersByRestaurantId(UUID restaurantId, CustomerOrderStatus customerOrderStatus) {
        return customerOrderStatus != null ?
                this.customerOrderRepository.findAllActiveOrdersByRestaurantIdAndStatus(restaurantId, customerOrderStatus.toString()) :
                this.customerOrderRepository.findAllActiveOrdersByRestaurantId(restaurantId);
    }
}
