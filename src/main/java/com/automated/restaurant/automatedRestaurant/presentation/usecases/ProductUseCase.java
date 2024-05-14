package com.automated.restaurant.automatedRestaurant.presentation.usecases;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateProductCategoryRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateProductRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateProductRequest;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Product;
import com.automated.restaurant.automatedRestaurant.presentation.entities.ProductCategory;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;

import java.util.List;
import java.util.UUID;

public interface ProductUseCase {

    Product findById(UUID id);

    List<Product> createAll(Restaurant restaurant, List<CreateProductRequest> requests);

    List<Product> updateAll(List<UpdateProductRequest> requests, Restaurant restaurant);

    void deleteAll(List<UUID> productIds);

    List<ProductCategory> findAllProductCategoriesByRestaurant(Restaurant restaurant);

    ProductCategory createProductCategory(Restaurant restaurant, CreateProductCategoryRequest request);

    void deleteAllProductCategories(List<UUID> categoriesIds);
}
