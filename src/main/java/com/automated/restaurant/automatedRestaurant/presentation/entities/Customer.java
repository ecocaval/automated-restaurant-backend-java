package com.automated.restaurant.automatedRestaurant.presentation.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Customer extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private UUID token;

    @Column(length = 2, nullable = false)
    private String cellPhoneAreaCode;

    @Column(length = 9, nullable = false)
    private String cellPhone;

    @Column(nullable = false)
    private Long commandIdentifier;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<SalesOrder> salesOrders;
}
