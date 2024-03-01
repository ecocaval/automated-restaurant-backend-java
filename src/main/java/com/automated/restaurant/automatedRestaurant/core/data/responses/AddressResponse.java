package com.automated.restaurant.automatedRestaurant.core.data.responses;

import com.automated.restaurant.automatedRestaurant.core.data.dtos.ViaCepDto;
import com.automated.restaurant.automatedRestaurant.core.data.enums.BrazilState;
import com.automated.restaurant.automatedRestaurant.core.utils.AsciiUtils;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Address;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressResponse {

    private UUID id;

    private String zipCode;

    private String name;

    private String complement;

    private String neighborhood;

    private String city;

    private BrazilState state;

    private String ibgeCode;

    public static AddressResponse fromViaCepDto(ViaCepDto viaCepDto) {
        return AddressResponse.builder()
                .zipCode(AsciiUtils.cleanString(viaCepDto.getCep()))
                .name(viaCepDto.getLogradouro())
                .complement(viaCepDto.getComplemento())
                .neighborhood(viaCepDto.getBairro())
                .city(viaCepDto.getLocalidade())
                .state(BrazilState.getFromString(viaCepDto.getUf()))
                .ibgeCode(viaCepDto.getIbge())
                .build();
    }

    public static AddressResponse fromAddress(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .zipCode(address.getZipCode())
                .name(address.getName())
                .complement(address.getComplement())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .ibgeCode(address.getIbgeCode())
                .build();
    }
}
