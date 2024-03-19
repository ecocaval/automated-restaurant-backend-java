package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.automated.restaurant.automatedRestaurant.core.data.dtos.ProductDto;
import com.automated.restaurant.automatedRestaurant.core.data.requests.PlaceOrderRequest;
import com.automated.restaurant.automatedRestaurant.core.utils.AsyncUtils;
import com.automated.restaurant.automatedRestaurant.presentation.entities.*;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.BilltNotFoundException;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.BIllRepository;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.CustomerOrderRepository;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.CustomerRepository;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class BillUseCaseImpl implements BillUseCase {

    @Autowired
    private BIllRepository bIllRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private CustomerUseCase customerUseCase;

    @Autowired
    private ProductUseCase productUseCase;

    @Override
    public Bill findById(UUID billId) {
        return this.bIllRepository.findById(billId).orElseThrow(() -> new BilltNotFoundException(billId));
    }

    @Override
    public List<Bill> findAllActiveBillsByRestaurantId(UUID restaurantId) {
        return this.bIllRepository.findAllActiveBillsByRestaurantId(restaurantId);
    }

    @Override
    public Optional<Bill> findByRestaurantTableAndActiveTrue(RestaurantTable restaurantTable) {
        return this.bIllRepository.findByRestaurantTableAndActiveTrue(restaurantTable);
    }

    @Override
    @Transactional
    public Bill bindCustomerToBill(
            Customer customer,
            Optional<Bill> optionalBill,
            RestaurantTable restaurantTable
    ) {
        customer.setRestaurantQueue(null);

        this.customerRepository.save(customer);

        if (optionalBill.isEmpty()) {
            var newBill = Bill.builder()
                    .restaurantTable(restaurantTable)
                    .customers(List.of(customer))
                    .build();

            return this.bIllRepository.save(newBill);
        }

        var bill = optionalBill.get();

        bill.getCustomers().add(customer);

        return this.bIllRepository.save(bill);
    }

    @Override
    public Bill placeOrderToBill(PlaceOrderRequest request, UUID billId) {

        CompletableFuture<Customer> customerFuture = AsyncUtils.getCompletableFuture(this.customerUseCase.findById(request.getCustomerId()));

        CompletableFuture<Bill> billFuture = AsyncUtils.getCompletableFuture(this.findById(billId));

        List<CompletableFuture<Product>> productFutures = request.getProductInformation().stream()
                .map(productInformation ->  AsyncUtils.getCompletableFuture(this.productUseCase.findById(productInformation.getProductId())))
                .toList();

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                customerFuture,
                billFuture,
                CompletableFuture.allOf(productFutures.toArray(new CompletableFuture[0]))
        );

        allFutures.join();

        Customer customer = null;
        List<Product> products = new ArrayList<>();
        Bill bill = null;

        try {
            customer = customerFuture.get();
            bill = billFuture.get();
            products = productFutures.stream()
                    .map(productFuture -> {
                        try {
                            return productFuture.get();
                        } catch (InterruptedException | ExecutionException e) {
                            Thread.currentThread().interrupt();
                            return null;
                        }
                    }).toList();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
        }

        if(customer == null || bill == null) {
            throw new RuntimeException();
        }

        var customerOrder = CustomerOrder.fromPlaceOrderRequest(customer, products, bill, request);

        this.customerOrderRepository.save(customerOrder);

        return this.findById(billId);
    }
}
