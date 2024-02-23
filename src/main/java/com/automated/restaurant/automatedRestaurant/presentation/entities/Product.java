package com.automated.restaurant.automatedRestaurant.presentation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@SQLDelete(sql = "UPDATE product SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
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
