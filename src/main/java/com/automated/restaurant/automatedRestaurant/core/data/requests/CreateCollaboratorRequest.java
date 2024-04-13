package com.automated.restaurant.automatedRestaurant.core.data.requests;

import com.automated.restaurant.automatedRestaurant.core.validations.Cpf;
import com.automated.restaurant.automatedRestaurant.core.validations.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateCollaboratorRequest {

    @NotBlank(message = "O nome do colaborador é obrigatório no cadastro.")
    private String name;

    @NotBlank(message = "O senha do colaborador é obrigatória no cadastro.")
    private String password;

    @Cpf
    private String cpf;

    @Email
    private String email;

    private Boolean isAdmin;

    private UUID jobTitleId;
}
