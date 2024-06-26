package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.core.data.enums.TableStatus;
import com.automated.restaurant.automatedRestaurant.presentation.entities.RestaurantTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RestaurantTableResponse {

    private UUID id;

    private boolean isCallingWaiter;

    private UUID restaurantId;

    private String identification;

    private Long capacity;

    private TableStatus status;

    public static RestaurantTableResponse fromRestaurantTable(RestaurantTable restaurantTable) {
        return RestaurantTableResponse.builder()
                .id(restaurantTable.getId())
                .isCallingWaiter(restaurantTable.isCallingWaiter())
                .restaurantId(restaurantTable.getRestaurant().getId())
                .identification(restaurantTable.getIdentification())
                .capacity(restaurantTable.getCapacity())
                .status(restaurantTable.getStatus())
                .build();
    }
}
