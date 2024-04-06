package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.core.data.enums.ProductOrderStatus;
import com.automated.restaurant.automatedRestaurant.presentation.entities.ProductOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductOrderResponse {

    private UUID id;

    private Long quantity;

    private ProductResponse product;

    private ProductOrderStatus status;

    public static ProductOrderResponse fromProductOrder(ProductOrder productOrder) {
        return ProductOrderResponse.builder()
                .id(productOrder.getId())
                .quantity(productOrder.getQuantity())
                .product(ProductResponse.fromProduct(productOrder.getProduct()))
                .status(productOrder.getStatus())
                .build();
    }
}
