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

    private String cpf;

    private String email;

    private Boolean isOwner;

    private Boolean isAdmin;

    private JobTitleResponse jobTitle;

    public static CollaboratorResponse fromCollaborator(Collaborator collaborator) {

        if(collaborator == null) {
            return null;
        }

        return CollaboratorResponse.builder()
                .id(collaborator.getId())
                .restaurantId(collaborator.getRestaurant().getId())
                .name(collaborator.getName())
                .cpf(collaborator.getCpf())
                .email(collaborator.getEmail())
                .isOwner(collaborator.getIsOwner())
                .isAdmin(collaborator.getIsAdmin())
                .jobTitle(JobTitleResponse.fromJobTitle(collaborator.getJobTitle()))
                .build();
    }
}
