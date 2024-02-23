package com.automated.restaurant.automatedRestaurant.presentation.entities;

import com.automated.restaurant.automatedRestaurant.core.data.enums.BrazilState;
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
@SQLDelete(sql = "UPDATE address SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Address extends BaseEntity {

    @Column(length = 8, nullable = false)
    private String zipCode;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 6)
    private String number;

    @Column(length = 50, nullable = false)
    private String neighborhood;

    @Column(length = 50)
    private String complement;

    @Column(nullable = false)
    private String city;

    @Column(length = 7)
    private String ibgeCode;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BrazilState state;

    @OneToOne(optional = false)
    private Restaurant restaurant;
}