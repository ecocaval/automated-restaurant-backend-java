package com.automated.restaurant.automatedRestaurant.presentation.repositories;

import com.automated.restaurant.automatedRestaurant.core.data.enums.ProductOrderStatus;
import com.automated.restaurant.automatedRestaurant.presentation.entities.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, UUID> {

    @Query(nativeQuery = true, value =
            "SELECT co.*" +
            "FROM bill b, restaurant r, restaurant_table rt, customer_order co " +
            "WHERE r.id = :restaurant_id AND " +
            "rt.restaurant_id = r.id AND " +
            "b.active IS TRUE AND " +
            "b.restaurant_table_id = rt.id AND " +
            "co.bill_id = b.id"
    )
    List<CustomerOrder> findAllActiveOrdersByRestaurantId(@Param("restaurant_id") UUID restaurantId);

    @Query(nativeQuery = true, value =
            "SELECT co.*" +
            "FROM bill b, restaurant r, restaurant_table rt, customer_order co " +
            "WHERE r.id = :restaurant_id AND " +
            "rt.restaurant_id = r.id AND " +
            "b.active IS TRUE AND " +
            "b.restaurant_table_id = rt.id AND " +
            "co.bill_id = b.id AND" +
            "co.status = :status"
    )
    List<CustomerOrder> findAllActiveOrdersByRestaurantIdAndStatus(
            @Param("restaurant_id") UUID restaurantId,
            @Param("status") ProductOrderStatus status
    );
}
