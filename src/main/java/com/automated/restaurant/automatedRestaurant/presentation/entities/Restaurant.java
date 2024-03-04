package com.automated.restaurant.automatedRestaurant.presentation.entities;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateRestaurantRequest;
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
@SQLDelete(sql = "UPDATE restaurant SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Restaurant extends BaseEntity {

    @Column(columnDefinition = "bool default TRUE", nullable = false)
    @Builder.Default
    private boolean active = true;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<Product> products;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<JobTitle> jobTitles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<Collaborator> collaborators;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<Customer> customers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<RestaurantImage> images;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<RestaurantTable> restaurantTables;

    public static Restaurant fromCreateRequest(CreateRestaurantRequest request) {

        return Restaurant.builder()
                .name(request.getName())
                .build();
    }
}
