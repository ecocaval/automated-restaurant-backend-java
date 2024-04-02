package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.core.data.enums.CustomerOrderStatus;
import com.automated.restaurant.automatedRestaurant.presentation.entities.CustomerOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomerOrderResponse {

    private Long quantity;

    private ProductResponse product;

    private CustomerOrderStatus status;

    public static CustomerOrderResponse fromOrder(CustomerOrder customerOrder) {
        return CustomerOrderResponse.builder()
                .quantity(customerOrder.getQuantity())
                .product(ProductResponse.fromProduct(customerOrder.getProduct()))
                .status(customerOrder.getStatus())
                .build();
    }
}
