package com.automated.restaurant.automatedRestaurant.presentation.repositories;

import com.automated.restaurant.automatedRestaurant.presentation.entities.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, UUID> {

    List<RestaurantTable> findAllByOrderByIdentificationAsc();

    Optional<RestaurantTable> findByRestaurantIdAndIdentification(UUID restaurantId, String identification);
}
