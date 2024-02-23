package com.automated.restaurant.automatedRestaurant.presentation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@SQLDelete(sql = "UPDATE customer SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
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
