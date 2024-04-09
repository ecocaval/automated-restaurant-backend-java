package com.automated.restaurant.automatedRestaurant.core.data.requests;

import com.automated.restaurant.automatedRestaurant.core.validations.Cpf;
import com.automated.restaurant.automatedRestaurant.core.validations.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateCollaboratorRequest {

    private String name;

    @Cpf
    private String cpf;

    @Email
    private String email;

    private Boolean isAdmin;

    private UUID jobTitleId;
}
