package com.automated.restaurant.automatedRestaurant.presentation.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageEntity extends BaseEntity {

    @Column
    private String name;

    @Column(nullable = false)
    private String type;

//    @Lob
    @Column(nullable = false)
    private byte[] imageData;

}
