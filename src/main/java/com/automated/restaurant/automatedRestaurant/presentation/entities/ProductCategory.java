package com.automated.restaurant.automatedRestaurant.presentation.entities;

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
@SQLDelete(sql = "UPDATE product_category SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class ProductCategory extends BaseEntity {

    public ProductCategory(String name, Restaurant restaurant) {
        this.name = name;
        this.restaurant = restaurant;
    }

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "product_product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "product_category_id"))
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (!this.products.contains(product)) {
            this.products.add(product);
        }
    }
}
