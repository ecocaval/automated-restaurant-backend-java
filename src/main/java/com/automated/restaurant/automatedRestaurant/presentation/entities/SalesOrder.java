package com.automated.restaurant.automatedRestaurant.presentation.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class SalesOrder extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salesOrder")
    private List<ProductInfo> productInfos;
}
