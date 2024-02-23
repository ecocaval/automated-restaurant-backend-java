package com.automated.restaurant.automatedRestaurant.presentation.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Restaurant extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<Product> products;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<JobTitle> jobTitles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<Admin> admins;
}
