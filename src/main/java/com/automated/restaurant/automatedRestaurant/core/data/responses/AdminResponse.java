package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.presentation.entities.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AdminResponse {

    private UUID id;

    private UUID restaurantId;

    private String name;

    private JobTitleResponse jobTitle;

    public static AdminResponse fromAdmin(Admin admin) {
        return AdminResponse.builder()
                .id(admin.getId())
                .restaurantId(admin.getRestaurant().getId())
                .name(admin.getName())
                .jobTitle(JobTitleResponse.fromJobTitle(admin.getJobTitle()))
                .build();
    }
}
