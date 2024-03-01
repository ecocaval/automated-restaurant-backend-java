package com.automated.restaurant.automatedRestaurant.presentation.repositories;

import com.automated.restaurant.automatedRestaurant.presentation.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByIdIn(Collection<UUID> productIds);
}
