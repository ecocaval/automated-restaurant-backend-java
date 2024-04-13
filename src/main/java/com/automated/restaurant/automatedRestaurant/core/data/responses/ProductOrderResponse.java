package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.core.data.enums.ProductOrderStatus;
import com.automated.restaurant.automatedRestaurant.presentation.entities.ProductOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductOrderResponse {

    private UUID id;

    private LocalDateTime creationDate;

    private LocalDateTime lastModifiedDate;

    private UUID customerOrderId;

    private Long quantity;

    private ProductResponse product;

    private ProductOrderStatus status;

    public static ProductOrderResponse fromProductOrder(ProductOrder productOrder) {
        return ProductOrderResponse.builder()
                .id(productOrder.getId())
                .creationDate(productOrder.getCreationDate())
                .lastModifiedDate(productOrder.getLastModifiedDate())
                .customerOrderId(productOrder.getCustomerOrder().getId())
                .quantity(productOrder.getQuantity())
                .product(ProductResponse.fromProduct(productOrder.getProduct()))
                .status(productOrder.getStatus())
                .build();
    }
}
