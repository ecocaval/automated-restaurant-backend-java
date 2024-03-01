package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.presentation.entities.Collaborator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CollaboratorResponse {

    private UUID id;

    private UUID restaurantId;

    private String name;

    private JobTitleResponse jobTitle;

    public static CollaboratorResponse fromCollaborator(Collaborator collaborator) {
        return CollaboratorResponse.builder()
                .id(collaborator.getId())
                .restaurantId(collaborator.getRestaurant().getId())
                .name(collaborator.getName())
                .jobTitle(JobTitleResponse.fromJobTitle(collaborator.getJobTitle()))
                .build();
    }
}
