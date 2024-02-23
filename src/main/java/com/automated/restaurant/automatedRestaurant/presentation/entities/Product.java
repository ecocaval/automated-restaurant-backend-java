package com.automated.restaurant.automatedRestaurant.presentation.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double value;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}
