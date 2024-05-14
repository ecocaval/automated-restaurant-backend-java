package com.automated.restaurant.automatedRestaurant.core.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {

    ERROR_INVALID_TOKEN("Por favor, envie um token válido para acessar este recurso."),
    ERROR_DURING_TOKEN_GENERATION("Um erro ocorreu ao tentar gerar token."),
    ERROR_REFRESH_TOKEN_NOT_FOUND_BY_ID("O refreshToken com este id não foi encontrado."),
    ERROR_RESTAURANT_NOT_FOUND_BY_ID("O restaurante com este id não foi encontrado."),
    ERROR_BILL_NOT_FOUND_BY_ID("A conta com este id não foi encontrada."),
    ERROR_COMBINATION_OF_CUSTOMER_AND_PRODUCT_ORDER_NOT_FOUND_BY_ID("O item do pedido com esta combinação de ids não econtrado."),
    ERROR_COLLABORATOR_NAME_IS_NULL_ON_CREATE_REQUEST("O nome do colaborador é obrigatório no cadastro."),
    ERROR_COLLABORATOR_PASSWORD_IS_NULL_ON_CREATE_REQUEST("A senha do colaborador é obrigatória no cadastro."),
    ERROR_INVALID_CPF("O cpf fornecido é invalido."),
    ERROR_INVALID_EMAIL("O email fornecido é invalido."),
    ERROR_CUSTOMER_ORDER_NOT_FOUND_BY_ID("O pedido com este id não foi encontrado."),
    ERROR_COLLABORATOR_NOT_FOUND_BY_ID("O colaborador com este id não foi encontrado."),
    ERROR_COLLABORATOR_NOT_FOUND_BY_USERNAME("O colaborador com este username não foi encontrada."),
    ERROR_COLLABORATOR_NOT_FOUND_BY_EMAIL("O colaborador com este e-mail não foi encontrada."),
    ERROR_COLLABORATOR_NOT_FOUND_BY_CPF("O colaborador com este cpf não foi encontrada."),
    ERROR_COLLABORATOR_INVALID_LOGIN("Não encontramos uma conta para o login fornecido."),
    ERROR_IMAGE_NOT_FOUND_BY_ID("A imagem com este id não foi encontrada."),
    ERROR_CUSTOMER_NOT_FOUND_BY_ID("O client com este id não foi encontrado."),
    ERROR_ON_IMAGE_STORE("Houve um problema ao tentar salvar a imagem, verifique a extensão e integridade do arquivo e tente novamente."),
    ERROR_NULL_UPDATE_OPERATION("O operação de update precisa ser informada: ADD, DELETE ou UPDATE."),
    ERROR_NULL_RESTAURANT_TABLE_IDENTIFICATION("O identificador da mesa do restaurante não pode ser nulo."),
    ERROR_NULL_RESTAURANT_TABLE_CAPACITY("A capacidade da mesa do restaurante não pode ser nula."),
    ERROR_DUPLICATED_JOB_TITLE("O cargo do restaurante deve ser único."),
    ERROR_DUPLICATED_PRODUCT("O nome do produto deve ser único."),
    ERROR_DUPLICATED_RESTAURANT_TABLE("O identificador da mesa do restaurante deve ser único."),
    ERROR_RESTAURANT_TABLE_NOT_FOUND_BY_ID("A mesa do restaurante com este id não foi encontrada."),
    ERROR_RESTAURANT_PRODUCT_NOT_FOUND_BY_ID("O produto do restaurante com este id não foi encontrada."),
    ERROR_RESTAURANT_PRODUCT_CATEGORY_NOT_FOUND_BY_ID("A categoria do produto com este id não foi encontrada."),
    ERROR_RESTAURANT_JOB_TITLE_NOT_FOUND_BY_ID("O cargo do restaurante com este id não foi encontrada."),
    ERROR_RESTAURANT_TABLE_CONFLICT_BY_IDENTIFICATION("A mesa do restaurante com este identificador já existe."),
    ERROR_RESTAURANT_PRODUCT_CONFLICT("O produto do restaurante com este nome, sku e tamanho de porção já existe."),
    ERROR_CUSTOMER_CONFLICT_BY_EMAIL("O cliente com este email já existe."),
    ERROR_PRODUCT_CATEGORY_CONFLICT_BY_NAME("A categoria de produto com este nome já existe."),
    ERROR_ADDRESS_NOT_FOUND_FOR_ZIP_CODE("Não encontramos um endereço para o cep fornecido, verifique o valor e tente novamente."),
    ERROR_NULL_COLLABORATOR_WHEN_CREATING_RESTAURANT("Ao menos um colaborador deve ser informado para criar o restaurante."),
    ERROR_EMAIL_OUT_OF_PATTERN("Por favor, insira um e-mail válido.");

    private final String message;
}
