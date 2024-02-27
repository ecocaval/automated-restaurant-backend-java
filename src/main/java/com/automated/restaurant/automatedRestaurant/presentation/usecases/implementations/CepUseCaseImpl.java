package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.automated.restaurant.automatedRestaurant.core.data.dtos.ViaCepDto;
import com.automated.restaurant.automatedRestaurant.core.utils.AsciiUtils;
import com.automated.restaurant.automatedRestaurant.presentation.clients.CepClient;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.CepNotFoundException;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.CepUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CepUseCaseImpl implements CepUseCase {

    private final CepClient cepClient;

    @Autowired
    public CepUseCaseImpl(CepClient cepClient) {
        this.cepClient = cepClient;
    }

    @Override
    public ViaCepDto getAddressByZipCode(String zipCode) {
        var address = cepClient.getAddressByZipCode(AsciiUtils.cleanString(zipCode));
        if(address.getCep() == null) {
            throw new CepNotFoundException(zipCode);
        }
        return address;
    }

}
