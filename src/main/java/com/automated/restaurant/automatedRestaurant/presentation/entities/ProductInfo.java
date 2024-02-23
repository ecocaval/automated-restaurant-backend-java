package com.automated.restaurant.automatedRestaurant.presentation.entities;

import jakarta.persistence.*;

@Entity
public class ProductInfo extends BaseEntity {

    @Column(nullable = false)
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sales_order_id", nullable = false)
    private SalesOrder salesOrder;
}
