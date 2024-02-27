package com.automated.restaurant.automatedRestaurant.core.data.requests;

import com.automated.restaurant.automatedRestaurant.core.data.enums.TableStatus;
import com.automated.restaurant.automatedRestaurant.core.data.enums.UpdateOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateRestaurantRequest {

    private String name;

    private List<UpdateJobTitleRequest> jobTitles;

    private List<UpdateRestaurantTableRequest> tables;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class UpdateJobTitleRequest {

        private UUID id;

        private String name;

        private UpdateOperation operation;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class UpdateRestaurantTableRequest {

        private UUID id;

        private UpdateOperation operation;

        private String identification;

        private Long capacity;

        private TableStatus status;
    }
}
