package com.automated.restaurant.automatedRestaurant.presentation.repositories;

import com.automated.restaurant.automatedRestaurant.presentation.entities.ProductCategory;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {

    boolean existsByNameIgnoreCaseAndRestaurant(String name, Restaurant restaurant);

    List<ProductCategory> findByRestaurant(Restaurant restaurant);

    List<ProductCategory> findByIdIn(List<UUID> ids);

}
