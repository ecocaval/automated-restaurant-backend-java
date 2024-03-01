package com.automated.restaurant.automatedRestaurant.presentation.entities;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateCustomerRequest;
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

    @Column(length = 2, nullable = false)
    private String cellPhoneAreaCode;

    @Column(length = 9, nullable = false)
    private String cellPhone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<CustomerOrder> customerOrders;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public static Customer fromCreateRequest(CreateCustomerRequest request, Restaurant restaurant) {
        return Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .cellPhoneAreaCode(request.getCellPhoneAreaCode())
                .cellPhone(request.getCellPhone())
                .restaurant(restaurant)
                .build();
    }
}
