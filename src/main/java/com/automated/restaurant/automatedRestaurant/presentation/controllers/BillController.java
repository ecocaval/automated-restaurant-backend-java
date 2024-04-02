package com.automated.restaurant.automatedRestaurant.presentation.controllers;

import com.automated.restaurant.automatedRestaurant.core.data.requests.PlaceOrderRequest;
import com.automated.restaurant.automatedRestaurant.core.data.responses.BillResponse;
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
    private ProductUseCase productUseCase;

    @GetMapping("restaurant/{restaurantId}")
    private ResponseEntity<List<BillResponse>> findAllActiveBills(
            @PathVariable("restaurantId") UUID restaurantId
    ) {
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
        var customer = this.customerUseCase.findById(customerId);

        var restaurantTable = this.restaurantTableUseCase.findById(tableId);

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

    @PostMapping("{billId}")
    private ResponseEntity<BillResponse> placeOrder(
            @PathVariable("billId") UUID billId,
            @RequestBody PlaceOrderRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            BillResponse.fromBill(this.billUseCase.placeOrderToBill(request, billId))
        );
    }

}
