package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.presentation.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductResponse {

    private UUID id;

    private boolean active;

    private UUID restaurantId;

    private String name;

    private Long servingCapacity;

    private Long sku;

//    private List<String> images;

    private String description;

    private Double price;

    public static ProductResponse fromProduct(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .active(product.isActive())
                .restaurantId(product.getRestaurant().getId())
                .name(product.getName())
                .servingCapacity(product.getServingCapacity())
                .sku(product.getSku())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
