package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RestaurantResponse {

    private UUID id;

    private String name;

    private List<ProductResponse> products;

    private List<JobTitleResponse> jobTitles;

    private List<CollaboratorResponse> collaborators;

    private List<RestaurantTableResponse> tables;

    public static RestaurantResponse fromRestaurant(Restaurant restaurant) {
        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .products(restaurant.getProducts() != null ? restaurant.getProducts().stream().map(ProductResponse::fromProduct).toList() : null)
                .jobTitles(restaurant.getJobTitles() != null ? restaurant.getJobTitles().stream().map(JobTitleResponse::fromJobTitle).toList() : null)
                .collaborators(restaurant.getCollaborators() != null ? restaurant.getCollaborators().stream().map(CollaboratorResponse::fromCollaborator).toList() : null)
                .tables(restaurant.getRestaurantTables() != null ? restaurant.getRestaurantTables().stream().map(RestaurantTableResponse::fromRestaurantTable).toList() : null)
                .build();
    }
}
