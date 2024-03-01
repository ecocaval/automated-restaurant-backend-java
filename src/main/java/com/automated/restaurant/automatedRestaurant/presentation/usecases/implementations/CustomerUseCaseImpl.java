package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateCustomerRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateCustomerRequest;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Customer;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.CustomerConflictException;
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

    @Override
    public Customer findById(UUID id) {
        return this.customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    public Customer create(CreateCustomerRequest request, Restaurant restaurant) {
        validateDuplicityOnEmail(request.getEmail(), restaurant.getId());

        return this.customerRepository.save(
                Customer.fromCreateRequest(request, restaurant)
        );
    }

    @Override
    public Customer update(UpdateCustomerRequest request, Customer customer) {
        validateDuplicityOnEmail(request.getEmail(), customer.getRestaurant().getId());

        Optional.ofNullable(request.getName()).ifPresent(customer::setName);
        Optional.ofNullable(request.getEmail()).ifPresent(customer::setEmail);
        Optional.ofNullable(request.getCellPhoneAreaCode()).ifPresent(customer::setCellPhoneAreaCode);
        Optional.ofNullable(request.getCellPhone()).ifPresent(customer::setCellPhone);

        return this.customerRepository.save(customer);
    }

    private void validateDuplicityOnEmail(String email, UUID restaurantId) {
        if (email == null || email.isEmpty() || email.isBlank()) return;
        if (this.customerRepository.existsByEmailAndRestaurantId(email, restaurantId)) {
            throw new CustomerConflictException(email);
        }
    }
}
