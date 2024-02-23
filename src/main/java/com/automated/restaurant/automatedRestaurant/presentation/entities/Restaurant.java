package com.automated.restaurant.automatedRestaurant.presentation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class Restaurant extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<Product> products;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<JobTitle> jobTitles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<Admin> admins;
}
