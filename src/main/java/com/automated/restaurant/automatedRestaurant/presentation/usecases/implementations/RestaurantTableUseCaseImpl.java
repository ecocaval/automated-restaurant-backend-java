package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateProductRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateRestaurantTableRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.OnTableUpdateRequest;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Product;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.entities.RestaurantTable;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.RestaurantTableConflictException;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.RestaurantTableNotFoundException;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.ProductRepository;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.RestaurantTableRepository;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.RestaurantTableUseCase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RestaurantTableUseCaseImpl implements RestaurantTableUseCase {

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<RestaurantTable> findAll() {
        return this.restaurantTableRepository.findAllByOrderByIdentificationAsc();
    }

    @Override
    public RestaurantTable findById(UUID id) {
        var optionalTable = this.restaurantTableRepository.findById(id);
        if (optionalTable.isEmpty()) {
            throw new RestaurantTableNotFoundException(id);
        }
        return optionalTable.get();
    }

    @Override
    @Transactional
    public List<RestaurantTable> createAll(Restaurant restaurant, List<CreateRestaurantTableRequest> requests) {

        List<RestaurantTable> tables = new ArrayList<>();

        for (CreateRestaurantTableRequest request : requests) {
            validateDuplicityOnTableIdentification(restaurant.getId(), request.getIdentification());
            tables.add(
                    this.restaurantTableRepository.saveAndFlush(RestaurantTable.fromCreateRequest(request, restaurant))
            );
        }

        return tables;
    }

    @Override
    public List<Product> createAllProducts(Restaurant restaurant, List<CreateProductRequest> requests) {
        List<Product> products = new ArrayList<>();

        for (CreateProductRequest request : requests) {
            products.add(
                this.productRepository.save(Product.fromCreateRequest(request, restaurant))
            );
        }

        return products;
    }

    @Override
    public void updateStatus(RestaurantTable oldRestaurantTable, OnTableUpdateRequest request) {
        this.restaurantTableRepository.saveAndFlush(
                new RestaurantTable(oldRestaurantTable, request.getStatus())
        );
    }

    private void validateDuplicityOnTableIdentification(UUID restaurantId, String identification) {
        if (this.restaurantTableRepository.findByRestaurantIdAndIdentification(restaurantId, identification).isPresent()) {
            throw new RestaurantTableConflictException(identification);
        }
    }
}
