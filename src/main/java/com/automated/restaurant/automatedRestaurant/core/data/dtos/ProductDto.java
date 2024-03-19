package com.automated.restaurant.automatedRestaurant.core.data.dtos;

import com.automated.restaurant.automatedRestaurant.presentation.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductDto extends Product {

    private Long quantity;

}