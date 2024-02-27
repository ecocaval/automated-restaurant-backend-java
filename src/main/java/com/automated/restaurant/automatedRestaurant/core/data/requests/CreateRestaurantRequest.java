package com.automated.restaurant.automatedRestaurant.core.data.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateRestaurantRequest {

    @NotNull
    private String name;

    private List<CreateRestaurantJobTitleRequest> jobTitles;

    private List<CreateRestaurantTableRequest> tables;
}
