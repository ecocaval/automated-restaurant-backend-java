package com.automated.restaurant.automatedRestaurant.core.data.requests;

import com.automated.restaurant.automatedRestaurant.core.data.enums.TableStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateTableRequest {

    private UUID id;

    private String identification;

    private Long capacity;

    private TableStatus status;
}
