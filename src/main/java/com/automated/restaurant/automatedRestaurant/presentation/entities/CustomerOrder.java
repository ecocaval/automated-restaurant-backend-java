package com.automated.restaurant.automatedRestaurant.presentation.entities;

import com.automated.restaurant.automatedRestaurant.core.data.enums.CustomerOrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "customer_order")
@SQLDelete(sql = "UPDATE customer_order SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class CustomerOrder extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerOrder")
    private List<ProductOrder> productOrders;

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private CustomerOrderStatus status = CustomerOrderStatus.PREPARING;
}
