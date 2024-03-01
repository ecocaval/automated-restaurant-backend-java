package com.automated.restaurant.automatedRestaurant.core.data.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateJobTitleRequest {

    private UUID id;

    private String name;
}
