package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.presentation.entities.JobTitle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class JobTitleResponse {

    private UUID id;

    private UUID restaurantId;

    private String name;

    public static JobTitleResponse fromJobTitle(JobTitle jobTitle) {

        if(jobTitle == null) {
            return null;
        }

        return JobTitleResponse.builder()
                .id(jobTitle.getId())
                .restaurantId(jobTitle.getRestaurant().getId())
                .name(jobTitle.getName())
                .build();
    }
}
