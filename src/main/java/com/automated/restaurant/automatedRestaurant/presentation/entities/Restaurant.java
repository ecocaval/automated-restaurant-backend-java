package com.automated.restaurant.automatedRestaurant.presentation.entities;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateRestaurantRequest;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@SQLDelete(sql = "UPDATE restaurant SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Restaurant extends BaseEntity {

    @Column(columnDefinition = "bool default TRUE", nullable = false)
    @Builder.Default
    private boolean active = true;

    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private RestaurantQueue restaurantQueue;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<Product> products;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<JobTitle> jobTitles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<Collaborator> collaborators;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "restaurants")
    private List<Customer> customers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<RestaurantTable> restaurantTables;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    @Builder.Default
    private List<ProductCategory> productCategories = new ArrayList<>();

    public static Restaurant fromCreateRequest(CreateRestaurantRequest request) {

        var restaurant =  Restaurant.builder()
                .name(request.getName())
                .build();

        restaurant.setRestaurantQueue(new RestaurantQueue(restaurant));

        return restaurant;
    }
}
