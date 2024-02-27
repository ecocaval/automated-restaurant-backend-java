package com.automated.restaurant.automatedRestaurant.presentation.entities;

import com.automated.restaurant.automatedRestaurant.core.data.enums.TableStatus;
import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateRestaurantTableRequest;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"identification", "restaurant_id"})})
public class RestaurantTable extends BaseEntity {

    @Column(nullable = false)
    private String identification;

    @Column(nullable = false)
    private Long capacity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TableStatus status = TableStatus.AVAILABLE;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public RestaurantTable(String identification, Long capacity, Restaurant restaurant) {
        this.identification = identification;
        this.capacity = capacity;
        this.restaurant = restaurant;
        this.status = TableStatus.AVAILABLE;
    }

    public RestaurantTable(RestaurantTable oldRestaurantTable, TableStatus newStatus) {
        super(
            oldRestaurantTable.getId(),
            oldRestaurantTable.getCreationDate(),
            oldRestaurantTable.getLastModifiedDate(),
            oldRestaurantTable.isDeleted()
        );
        this.identification = oldRestaurantTable.getIdentification();
        this.restaurant = oldRestaurantTable.getRestaurant();
        this.status = newStatus;
    }

    public static RestaurantTable fromCreateRequest(CreateRestaurantTableRequest request, Restaurant restaurant) {
        return RestaurantTable.builder()
                .identification(request.getIdentification())
                .capacity(request.getCapacity())
                .restaurant(restaurant)
                .build();
    }
}
