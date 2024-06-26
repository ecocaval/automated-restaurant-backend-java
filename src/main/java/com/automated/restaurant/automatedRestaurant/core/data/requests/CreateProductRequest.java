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
public class CreateProductRequest {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Double price;

    private List<UUID> productCategoriesIds;

    private Long servingCapacity;

    private Long sku;
}
