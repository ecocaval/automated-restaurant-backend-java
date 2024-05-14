package com.automated.restaurant.automatedRestaurant.core.data.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateProductRequest {

    private UUID id;

    private Boolean active;

    private String name;

    private String description;

    private List<UUID> productCategoriesIds;

    private Double price;

    private Long servingCapacity;

    private Long sku;
}
