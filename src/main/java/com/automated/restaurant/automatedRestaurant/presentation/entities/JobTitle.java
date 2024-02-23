package com.automated.restaurant.automatedRestaurant.presentation.entities;

import jakarta.persistence.*;

@Entity
public class JobTitle extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}
