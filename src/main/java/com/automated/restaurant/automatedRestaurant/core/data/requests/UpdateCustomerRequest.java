package com.automated.restaurant.automatedRestaurant.core.data.requests;

import com.automated.restaurant.automatedRestaurant.core.validations.AreaCode;
import com.automated.restaurant.automatedRestaurant.core.validations.CellPhone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateCustomerRequest {

    private String name;

    private String email;

    @AreaCode
    private String cellPhoneAreaCode;

    @CellPhone
    private String cellPhone;
}
