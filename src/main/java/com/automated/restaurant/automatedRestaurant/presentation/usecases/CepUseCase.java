package com.automated.restaurant.automatedRestaurant.presentation.usecases;

import com.automated.restaurant.automatedRestaurant.core.data.dtos.ViaCepDto;

public interface CepUseCase {

    ViaCepDto getAddressByZipCode(String zipCode);

}
