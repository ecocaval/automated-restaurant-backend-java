package com.automated.restaurant.automatedRestaurant.presentation.repositories;

import com.automated.restaurant.automatedRestaurant.presentation.entities.Bill;
import com.automated.restaurant.automatedRestaurant.presentation.entities.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BIllRepository extends JpaRepository<Bill, UUID> {

    Optional<Bill> findByRestaurantTableAndActiveTrue(RestaurantTable restaurantTable);

    @Query(nativeQuery = true, value =
            "SELECT b.*" +
            "FROM bill b, restaurant r, restaurant_table rt " +
            "WHERE r.id = :restaurant_id AND " +
            "rt.restaurant_id = r.id AND " +
            "b.active IS TRUE AND " +
            "b.restaurant_table_id = rt.id"
    )
    List<Bill> findAllActiveBillsByRestaurantId(@Param("restaurant_id") UUID restaurantId);
}
