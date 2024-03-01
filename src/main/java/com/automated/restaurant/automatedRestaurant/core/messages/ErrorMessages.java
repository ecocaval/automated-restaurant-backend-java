package com.automated.restaurant.automatedRestaurant.core.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {

    ERROR_RESTAURANT_NOT_FOUND_BY_ID("O restaurante com este id não foi encontrado."),
    ERROR_IMAGE_NOT_FOUND_BY_ID("A imagem com este id não foi encontrada."),
    ERROR_ON_IMAGE_STORE("Houve um problema ao tentar salvar a imagem, verifique a extensão e integridade do arquivo e tente novamente."),
    ERROR_NULL_UPDATE_OPERATION("O operação de update precisa ser informada: ADD, DELETE ou UPDATE."),
    ERROR_NULL_RESTAURANT_TABLE_IDENTIFICATION("O identificador da mesa do restaurante não pode ser nulo."),
    ERROR_NULL_RESTAURANT_TABLE_CAPACITY("A capacidade da mesa do restaurante não pode ser nula."),
    ERROR_DUPLICATED_JOB_TITLE("O cargo do restaurante deve ser único."),
    ERROR_DUPLICATED_PRODUCT("O nome do produto deve ser único."),
    ERROR_DUPLICATED_RESTAURANT_TABLE("O identificador da mesa do restaurante deve ser único."),
    ERROR_RESTAURANT_TABLE_NOT_FOUND_BY_ID("A mesa do restaurante com este id não foi encontrada."),
    ERROR_RESTAURANT_PRODUCT_NOT_FOUND_BY_ID("O produto do restaurante com este id não foi encontrada."),
    ERROR_RESTAURANT_JOB_TITLE_NOT_FOUND_BY_ID("O cargo do restaurante com este id não foi encontrada."),
    ERROR_RESTAURANT_TABLE_CONFLICT_BY_IDENTIFICATION("A mesa do restaurante com este identificador já existe."),
    ERROR_ADDRESS_NOT_FOUND_FOR_ZIP_CODE("Não encontramos um endereço para o cep fornecido, verifique o valor e tente novamente."),
    ERROR_EMAIL_OUT_OF_PATTERN("Por favor, insira um e-mail válido.");

    private final String message;
}
