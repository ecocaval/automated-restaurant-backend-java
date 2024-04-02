package com.automated.restaurant.automatedRestaurant.core.data.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaceCustomerOrdersRequest {

    @NotNull(message = "O id do cliente ao fazer o pedido não pode ser nulo.")
    private UUID customerId;

    private List<ProductInformation> productInformation;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ProductInformation {

        @NotNull(message = "O id do produto ao fazer o pedido não pode ser nulo.")
        private UUID productId;

        private Long quantity = 1L;
    }
}
