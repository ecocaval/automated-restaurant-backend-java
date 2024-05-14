package com.automated.restaurant.automatedRestaurant.presentation.controllers;

import com.automated.restaurant.automatedRestaurant.core.data.enums.CustomerOrderStatus;
import com.automated.restaurant.automatedRestaurant.core.data.requests.PlaceCustomerOrdersRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateCustomerOrdersRequest;
import com.automated.restaurant.automatedRestaurant.core.data.responses.BillResponse;
import com.automated.restaurant.automatedRestaurant.core.data.responses.CustomerOrderResponse;
import com.automated.restaurant.automatedRestaurant.core.infra.security.JwtUtils;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Bill;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/bill")
public class BillController {

    @Autowired
    private RestaurantUseCase restaurantUseCase;

    @Autowired
    private CustomerUseCase customerUseCase;

    @Autowired
    private RestaurantTableUseCase restaurantTableUseCase;

    @Autowired
    private BillUseCase billUseCase;

    @Autowired
    private OrderUseCase orderUseCase;

    @Autowired
    private ProductUseCase productUseCase;

    @GetMapping("restaurant/{restaurantId}")
    private ResponseEntity<List<BillResponse>> findAllActiveBills(
            @PathVariable("restaurantId") UUID restaurantId
    ) {
        JwtUtils.validateAdminOrRestaurantCollaborator(restaurantId.toString());

        List<Bill> bills = this.billUseCase.findAllActiveBillsByRestaurantId(restaurantId);

        return ResponseEntity.status(HttpStatus.OK).body(
                bills.stream().map(BillResponse::fromBill).toList()
        );
    }

    @PostMapping("customer/{customerId}/table/{tableId}")
    private ResponseEntity<BillResponse> bindCustomerToBill(
            @PathVariable("customerId") UUID customerId,
            @PathVariable("tableId") UUID tableId
    ) {
        var restaurantTable = this.restaurantTableUseCase.findById(tableId);

        JwtUtils.validateAdminOrRestaurantCollaborator(restaurantTable.getRestaurant().getId().toString());

        var customer = this.customerUseCase.findById(customerId);

        var optionalExistingBillForTable = this.billUseCase.findByRestaurantTableAndActiveTrue(restaurantTable);

        return ResponseEntity.status(HttpStatus.OK).body(
                BillResponse.fromBill(
                        this.billUseCase.bindCustomerToBill(customer, optionalExistingBillForTable, restaurantTable)
                )
        );
    }

    @GetMapping("{billId}")
    private ResponseEntity<BillResponse> findById(
            @PathVariable("billId") UUID billId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                BillResponse.fromBill(this.billUseCase.findById(billId))
        );
    }

    @GetMapping("/orders/restaurant/{restaurantId}")
    private ResponseEntity<List<CustomerOrderResponse>> getActiveOrdersInRestaurant(
            @PathVariable("restaurantId") UUID restaurantId,
            @RequestParam(value = "customerOrderStatus", required = false) CustomerOrderStatus customerOrderStatus
    ) {
        JwtUtils.validateAdminOrRestaurantCollaborator(restaurantId.toString());

        this.restaurantUseCase.findById(restaurantId);

        return ResponseEntity.status(HttpStatus.OK).body(
                this.orderUseCase.findActiveOrdersByRestaurantId(restaurantId, customerOrderStatus)
                        .stream()
                        .map(CustomerOrderResponse::fromCustomerOrder)
                        .toList()
        );
    }

    @PostMapping("{billId}/orders")
    private ResponseEntity<BillResponse> placeOrders(
            @PathVariable("billId") UUID billId,
            @RequestBody PlaceCustomerOrdersRequest request
    ) {
        this.billUseCase.findById(billId);

        return ResponseEntity.status(HttpStatus.OK).body(
            BillResponse.fromBill(this.billUseCase.placeOrders(request, billId))
        );
    }

    @PatchMapping("{billId}/orders")
    private ResponseEntity<BillResponse> updateOrders(
            @PathVariable("billId") UUID billId,
            @RequestBody UpdateCustomerOrdersRequest request
    ) {
        this.billUseCase.findById(billId);

        return ResponseEntity.status(HttpStatus.OK).body(
                BillResponse.fromBill(this.billUseCase.updateOrders(request, billId))
        );
    }

}
