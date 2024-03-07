package com.automated.restaurant.automatedRestaurant.presentation.entities;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateProductRequest;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@SQLDelete(sql = "UPDATE product SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Product extends BaseEntity {

    @Column(columnDefinition = "bool default TRUE", nullable = false)
    @Builder.Default
    private boolean active = true;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Builder.Default
    private Long servingCapacity = 1L;

    @Column(nullable = false)
    @Builder.Default
    private Long sku = (long) Math.floor(1_000_000 * Math.random());

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<ProductImage> images;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public static Product fromCreateRequest(CreateProductRequest request, Restaurant restaurant) {
        var product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .restaurant(restaurant)
                .build();

        if(request.getServingCapacity() != null) {
            product.setServingCapacity(request.getServingCapacity());
        }

        if(request.getSku() != null) {
            product.setSku(request.getSku());
        }

        return product;
    }
}
