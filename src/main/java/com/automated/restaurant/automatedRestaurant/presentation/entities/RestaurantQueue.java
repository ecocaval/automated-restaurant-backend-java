package com.automated.restaurant.automatedRestaurant.presentation.entities;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateProductRequest;
import jakarta.persistence.*;
import lombok.*;
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
//@SQLDelete(sql = "UPDATE product SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class RestaurantQueue extends BaseEntity {

    @OneToMany(mappedBy = "restaurantQueue")
    private List<Customer> customers;

    @OneToOne
    private Restaurant restaurant;

    public RestaurantQueue(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
