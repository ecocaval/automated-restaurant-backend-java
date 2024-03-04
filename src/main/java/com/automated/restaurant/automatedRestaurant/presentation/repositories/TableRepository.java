package com.automated.restaurant.automatedRestaurant.presentation.repositories;

import com.automated.restaurant.automatedRestaurant.presentation.entities.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface TableRepository extends JpaRepository<RestaurantTable, UUID> {

    List<RestaurantTable> findAllByOrderByIdentificationAsc();

    List<RestaurantTable> findByIdIn(Collection<UUID> tableIds);

    Boolean existsByRestaurantIdAndIdentification(UUID restaurantId, String identification);

    Boolean existsByRestaurantIdAndIdentificationAndIdNotIn(UUID restaurantId, String identification, List<UUID> tableIds);}
