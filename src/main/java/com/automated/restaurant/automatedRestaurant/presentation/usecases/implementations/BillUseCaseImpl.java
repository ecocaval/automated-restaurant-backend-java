package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.automated.restaurant.automatedRestaurant.core.data.dtos.CustomerOrderMessageDto;
import com.automated.restaurant.automatedRestaurant.core.data.dtos.CustomerRestaurantQueueMessageDto;
import com.automated.restaurant.automatedRestaurant.core.data.dtos.RestaurantBillMessageDto;
import com.automated.restaurant.automatedRestaurant.core.data.enums.RestaurantBillAction;
import com.automated.restaurant.automatedRestaurant.core.data.enums.RestaurantQueueAction;
import com.automated.restaurant.automatedRestaurant.core.data.enums.TableStatus;
import com.automated.restaurant.automatedRestaurant.core.data.requests.PlaceOrderRequest;
import com.automated.restaurant.automatedRestaurant.core.data.responses.BillResponse;
import com.automated.restaurant.automatedRestaurant.core.data.responses.CustomerOrderResponse;
import com.automated.restaurant.automatedRestaurant.core.data.responses.TableResponse;
import com.automated.restaurant.automatedRestaurant.core.utils.AsyncUtils;
import com.automated.restaurant.automatedRestaurant.presentation.entities.*;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.BilltNotFoundException;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.BIllRepository;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.CustomerOrderRepository;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.CustomerRepository;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.RestaurantTableRepository;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private RestaurantTableRepository restaurantTableRepository;

    @Autowired
    private CustomerUseCase customerUseCase;

    @Autowired
    private ProductUseCase productUseCase;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

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

            var persistedBill = this.bIllRepository.save(newBill);

            setRestaurantTableAsOccupied(restaurantTable);

            this.messagingTemplate.convertAndSend(
                    String.format("/topic/restaurant/%s/queue", restaurantTable.getRestaurant().getId()),
                    new CustomerRestaurantQueueMessageDto(RestaurantQueueAction.LEFT, customer)
            );

            this.messagingTemplate.convertAndSend(
                    String.format("/topic/restaurant/%s/bill", restaurantTable.getRestaurant().getId()),
                    new RestaurantBillMessageDto(RestaurantBillAction.CREATED, BillResponse.fromBill(persistedBill))
            );

            return persistedBill;
        }

        var bill = optionalBill.get();

        bill.getCustomers().add(customer);

        setRestaurantTableAsOccupied(restaurantTable);

        bill.setRestaurantTable(restaurantTable);

        this.restaurantTableRepository.save(restaurantTable);

        var persistedBill = this.bIllRepository.save(bill);

        this.messagingTemplate.convertAndSend(
                String.format("/topic/restaurant/%s/queue", restaurantTable.getRestaurant().getId()),
                new CustomerRestaurantQueueMessageDto(RestaurantQueueAction.LEFT, customer)
        );

        this.messagingTemplate.convertAndSend(
                String.format("/topic/restaurant/%s/bill", restaurantTable.getRestaurant().getId()),
                new RestaurantBillMessageDto(RestaurantBillAction.UPDATED, BillResponse.fromBill(persistedBill))
        );

        this.messagingTemplate.convertAndSend(
                String.format("/topic/restaurant//bill/%s", persistedBill.getId()),
                new RestaurantBillMessageDto(RestaurantBillAction.UPDATED, BillResponse.fromBill(persistedBill))
        );

        return persistedBill;
    }

    @Override
    public Bill placeOrderToBill(PlaceOrderRequest request, UUID billId) {

        CompletableFuture<Customer> customerFuture = AsyncUtils.getCompletableFuture(this.customerUseCase.findById(request.getCustomerId()));

        CompletableFuture<Bill> billFuture = AsyncUtils.getCompletableFuture(this.findById(billId));

        List<CompletableFuture<Product>> productFutures = request.getProductInformation().stream()
                .map(productInformation ->  AsyncUtils.getCompletableFuture(this.productUseCase.findById(productInformation.getProductId())))
                .toList();

        AsyncUtils.completeFutures(
                customerFuture,
                billFuture,
                CompletableFuture.allOf(productFutures.toArray(new CompletableFuture[0]))
        );

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
            throw new RuntimeException(); //FIXME
        }

        var customerOrder = CustomerOrder.fromPlaceOrderRequest(customer, products, bill, request);

        this.customerOrderRepository.save(customerOrder);

        var persistedBill = this.findById(billId);

        this.messagingTemplate.convertAndSend(
                String.format("/topic/restaurant/%s/orders", persistedBill.getRestaurantTable().getRestaurant().getId()),
                new CustomerOrderMessageDto(
                        persistedBill.getRestaurantTable().getIdentification(),
                        CustomerOrderResponse.fromCustomerOrder(customerOrder)
                )
        );

        this.messagingTemplate.convertAndSend(
                String.format("/topic/restaurant/%s/bill", persistedBill.getRestaurantTable().getRestaurant().getId()),
                new RestaurantBillMessageDto(RestaurantBillAction.UPDATED, BillResponse.fromBill(persistedBill))
        );

        this.messagingTemplate.convertAndSend(
                String.format("/topic/restaurant/bill/%s", persistedBill.getId()),
                new RestaurantBillMessageDto(RestaurantBillAction.UPDATED, BillResponse.fromBill(persistedBill))
        );

        return persistedBill;
    }

    private void setRestaurantTableAsOccupied(RestaurantTable restaurantTable) {

        restaurantTable.setStatus(TableStatus.OCCUPIED);

        restaurantTableRepository.save(restaurantTable);

        this.messagingTemplate.convertAndSend(
                String.format("/topic/restaurant/%s/table", restaurantTable.getRestaurant().getId()),
                TableResponse.fromRestaurantTable(restaurantTable)
        );
    }
}
