package com.automated.restaurant.automatedRestaurant.presentation.controllers;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateCustomerRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateCustomerRequest;
import com.automated.restaurant.automatedRestaurant.core.data.responses.BillResponse;
import com.automated.restaurant.automatedRestaurant.core.data.responses.CustomerResponse;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.BillUseCase;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.CustomerUseCase;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.RestaurantUseCase;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.TableUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    @Autowired
    private RestaurantUseCase restaurantUseCase;

    @Autowired
    private CustomerUseCase customerUseCase;

    @Autowired
    private TableUseCase tableUseCase;

    @Autowired
    private BillUseCase billUseCase;

    @GetMapping("/{customerId}")
    private ResponseEntity<CustomerResponse> findById(
            @PathVariable("customerId") UUID customerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                CustomerResponse.fromCustomer(this.customerUseCase.findById(customerId))
        );
    }

    @GetMapping("/restaurant/{restaurantId}")
    private ResponseEntity<List<CustomerResponse>> findCustomersByRestaurantId(
            @PathVariable("restaurantId") UUID restaurantId
    ) {
        var restaurant = this.restaurantUseCase.findById(restaurantId);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                restaurant.getCustomers().stream().map(CustomerResponse::fromCustomer).toList()
        );
    }

    @PostMapping("/restaurant/{restaurantId}")
    private ResponseEntity<CustomerResponse> create(
            @PathVariable("restaurantId") UUID restaurantId,
            @RequestBody @Valid CreateCustomerRequest request
    ) {
        var restaurant = this.restaurantUseCase.findById(restaurantId);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                CustomerResponse.fromCustomer(this.customerUseCase.create(request, restaurant))
        );
    }

    @PatchMapping("/{customerId}")
    private ResponseEntity<CustomerResponse> update(
            @PathVariable("customerId") UUID customerId,
            @RequestBody @Valid UpdateCustomerRequest request
    ) {
        var customer = this.customerUseCase.findById(customerId);

        return ResponseEntity.status(HttpStatus.OK).body(
                CustomerResponse.fromCustomer(this.customerUseCase.update(request, customer))
        );
    }

}
