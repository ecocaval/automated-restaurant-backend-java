package com.automated.restaurant.automatedRestaurant.presentation.entities;

import com.automated.restaurant.automatedRestaurant.core.data.requests.PlaceOrderRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@SQLDelete(sql = "UPDATE customer_order SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class CustomerOrder extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerOrder")
    private List<ProductOrderInfo> productOrderInfo;

    public static CustomerOrder fromPlaceOrderRequest(Customer customer, List<Product> products, Bill bill, PlaceOrderRequest request) {

        var customerOrder = CustomerOrder.builder()
                .customer(customer)
                .bill(bill)
                .build();

        List<ProductOrderInfo> productOrderInfoList = new ArrayList<>();

        products.forEach(product -> {
            productOrderInfoList.add(
                    new ProductOrderInfo(
                            request.getProductInformation().stream().filter(
                                    productInformation -> productInformation.getProductId().equals(product.getId())
                            ).toList().get(0).getQuantity(),
                            product,
                            customerOrder
                    )
            );
        });

        customerOrder.setProductOrderInfo(productOrderInfoList);

        return customerOrder;
    }
}
