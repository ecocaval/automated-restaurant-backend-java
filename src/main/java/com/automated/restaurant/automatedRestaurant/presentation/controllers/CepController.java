package com.automated.restaurant.automatedRestaurant.presentation.controllers;

import com.automated.restaurant.automatedRestaurant.core.data.responses.AddressResponse;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.CepUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/cep")
public class CepController {

    @Autowired
    private CepUseCase cepUseCase;

    @GetMapping("zip-code/{zipCode}")
    public ResponseEntity<?> getAddressByZipCode(@PathVariable("zipCode") String zipCode) {

        return ResponseEntity.status(HttpStatus.OK).body(
                AddressResponse.fromViaCepDto(cepUseCase.getAddressByZipCode(zipCode))
        );
    }
}
