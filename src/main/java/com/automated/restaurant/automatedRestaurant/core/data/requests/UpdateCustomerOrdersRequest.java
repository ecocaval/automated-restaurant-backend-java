package com.automated.restaurant.automatedRestaurant.core.data.requests;

import com.automated.restaurant.automatedRestaurant.core.data.enums.CustomerOrderStatus;
import com.automated.restaurant.automatedRestaurant.core.data.enums.ProductOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateCustomerOrdersRequest {

    private List<CustomerOrderInformation> customerOrderInformation;

    private List<ProductOrderInformation> productOrderInformation;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class CustomerOrderInformation {

        @NotNull(message = "O id do pedido não ser nulo.")
        private UUID customerOrderId;

        private CustomerOrderStatus status;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ProductOrderInformation {

        @NotNull(message = "O id do pedido não ser nulo.")
        private UUID customerOrderId;

        @NotNull(message = "O id do item do pedido não ser nulo.")
        private UUID productOrderId;

        private ProductOrderStatus status;

        private Long quantity;
    }
}
