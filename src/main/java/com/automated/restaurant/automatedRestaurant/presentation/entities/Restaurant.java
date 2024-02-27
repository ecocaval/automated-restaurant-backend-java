package com.automated.restaurant.automatedRestaurant.presentation.entities;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateRestaurantRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<Product> products;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<JobTitle> jobTitles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<Admin> admins;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<RestaurantTable> tables;

    public static Restaurant fromCreateRequest(CreateRestaurantRequest request) {

        var restaurant = Restaurant.builder()
                .name(request.getName())
                .jobTitles(request.getJobTitles() != null ? new ArrayList<>() : null)
                .tables(request.getTables() != null ? new ArrayList<>() : null)
                .build();

        request.getJobTitles().forEach(jobTitleRequest -> {
            restaurant.getJobTitles().add(new JobTitle(jobTitleRequest.getName(), restaurant));
        });

        request.getTables().forEach(tableRequest -> {
            restaurant.getTables().add(RestaurantTable.fromCreateRequest(tableRequest, restaurant));
        });

        return restaurant;
    }
}
