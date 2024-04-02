package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.core.data.enums.OrderStatus;
import com.automated.restaurant.automatedRestaurant.presentation.entities.CustomerOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderResponse {

    private Long quantity;

    private ProductResponse product;

    private OrderStatus status;

    public static OrderResponse fromOrder(CustomerOrder customerOrder) {
        return OrderResponse.builder()
                .quantity(customerOrder.getQuantity())
                .product(ProductResponse.fromProduct(customerOrder.getProduct()))
                .status(customerOrder.getStatus())
                .build();
    }
}
