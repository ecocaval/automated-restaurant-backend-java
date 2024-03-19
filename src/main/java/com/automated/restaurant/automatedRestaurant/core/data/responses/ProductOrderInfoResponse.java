package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.presentation.entities.ProductOrderInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductOrderInfoResponse {

    private Long quantity;

    private ProductResponse product;

    public static ProductOrderInfoResponse fromProductOrderInfo(ProductOrderInfo productOrderInfo) {
        return ProductOrderInfoResponse.builder()
                .quantity(productOrderInfo.getQuantity())
                .product(ProductResponse.fromProduct(productOrderInfo.getProduct()))
                .build();
    }
}
