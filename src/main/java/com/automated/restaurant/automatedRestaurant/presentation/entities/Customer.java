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

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "restaurant_customer",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id"))
    private List<Restaurant> restaurants;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "customers")
    private List<Bill> bills;

    @ManyToOne
    @JoinColumn(name = "restaurant_queue_id")
    private RestaurantQueue restaurantQueue;

    public static Customer fromCreateRequest(CreateCustomerRequest request, Restaurant restaurant) {
        return Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .cellPhoneAreaCode(request.getCellPhoneAreaCode())
                .cellPhone(request.getCellPhone())
                .restaurants(List.of(restaurant))
                .restaurantQueue(restaurant.getRestaurantQueue())
                .build();
    }
}
