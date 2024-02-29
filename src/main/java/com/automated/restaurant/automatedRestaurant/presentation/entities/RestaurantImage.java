package com.automated.restaurant.automatedRestaurant.presentation.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RestaurantImage extends ImageEntity {

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    @Column(nullable = false)
    @Builder.Default
    private boolean isLogo = false;

    @Column
    private Long priority;

    public void setLogoPriority() {
        if (this.isLogo) this.priority = 0L;
    }
}
