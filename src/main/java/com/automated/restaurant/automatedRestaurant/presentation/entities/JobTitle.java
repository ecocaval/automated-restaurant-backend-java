package com.automated.restaurant.automatedRestaurant.presentation.entities;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateJobTitleRequest;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "restaurant_id"})})
public class JobTitle extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public JobTitle(String name, Restaurant restaurant) {
        this.name = name;
        this.restaurant = restaurant;
    }

    public static JobTitle fromCreateRequest(CreateJobTitleRequest request, Restaurant restaurant) {
        return JobTitle.builder()
                .name(request.getName())
                .restaurant(restaurant)
                .build();
    }
}
