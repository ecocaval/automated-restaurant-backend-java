package com.automated.restaurant.automatedRestaurant.presentation.repositories;

import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
}
