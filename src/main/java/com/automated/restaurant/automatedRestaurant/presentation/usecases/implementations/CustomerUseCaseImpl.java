package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.automated.restaurant.automatedRestaurant.core.data.dtos.CustomerRestaurantQueueMessageDto;
import com.automated.restaurant.automatedRestaurant.core.data.enums.RestaurantQueueAction;
import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateCustomerRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateCustomerRequest;
import com.automated.restaurant.automatedRestaurant.presentation.clients.SocketIoApiClient;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Customer;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.CustomerNotFoundException;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.CustomerRepository;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.CustomerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerUseCaseImpl implements CustomerUseCase {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SocketIoApiClient socketIoApiClient;

    @Override
    public Customer findById(UUID id) {
        return this.customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    public Customer create(CreateCustomerRequest request, Restaurant restaurant) {

        var optionalAlreadyExistingCustomer = this.customerRepository.findByEmailAndCellPhoneAreaCodeAndCellPhone(
                request.getEmail(), request.getCellPhoneAreaCode(), request.getCellPhone()
        );

        if(optionalAlreadyExistingCustomer.isEmpty()) {

            var customer = this.customerRepository.save(Customer.fromCreateRequest(request, restaurant));

            this.socketIoApiClient.publishCustomerQueueEvent(
                    new CustomerRestaurantQueueMessageDto(RestaurantQueueAction.ENTERED, customer),
                    String.valueOf(restaurant.getId())
            );

            return customer;
        }

        /* FIXME: should send SMS to confirm if it is the real person, otherwise sensitive
            data like bills or so will be extremely accessible. */

        var customer = optionalAlreadyExistingCustomer.get();

        customer.setRestaurantQueue(restaurant.getRestaurantQueue());

        if (!customer.getRestaurants().contains(restaurant)) {
            customer.getRestaurants().add(restaurant);
        }

        this.customerRepository.save(customer);

        this.socketIoApiClient.publishCustomerQueueEvent(
                new CustomerRestaurantQueueMessageDto(RestaurantQueueAction.ENTERED, customer),
                String.valueOf(restaurant.getId())
        );
        return customer;
    }

    @Override
    public Customer update(UpdateCustomerRequest request, Customer customer) {

        /* FIXME: should confirm if the customer built in here is not identical to another existent one. */

        Optional.ofNullable(request.getName()).ifPresent(customer::setName);
        Optional.ofNullable(request.getEmail()).ifPresent(customer::setEmail);
        Optional.ofNullable(request.getCellPhoneAreaCode()).ifPresent(customer::setCellPhoneAreaCode);
        Optional.ofNullable(request.getCellPhone()).ifPresent(customer::setCellPhone);

        return this.customerRepository.save(customer);
    }
}
