package com.automated.restaurant.automatedRestaurant.core.data.requests;

import com.automated.restaurant.automatedRestaurant.core.validations.AreaCode;
import com.automated.restaurant.automatedRestaurant.core.validations.CellPhone;
import com.automated.restaurant.automatedRestaurant.presentation.entities.CustomerOrder;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateCustomerRequest {

    @NotNull(message = "O nome do cliente é obrigatório no cadastro.")
    private String name;

    @NotNull(message = "O email do cliente é obrigatório no cadastro.")
    private String email;

    @AreaCode
    @NotNull(message = "O código de área do celular é obrigatório no cadastro.")
    private String cellPhoneAreaCode;

    @CellPhone
    @NotNull
    @NotNull(message = "O celular é obrigatório no cadastro.")
    private String cellPhone;
}
