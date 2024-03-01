package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateTableRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.OnTableUpdateRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateTableRequest;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.entities.RestaurantTable;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.TableConflictException;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.TableNotFoundException;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.ProductRepository;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.TableRepository;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.TableUseCase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TableUseCaseImpl implements TableUseCase {

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<RestaurantTable> findAll() {
        return this.tableRepository.findAllByOrderByIdentificationAsc();
    }

    @Override
    public RestaurantTable findById(UUID id) {
        var optionalTable = this.tableRepository.findById(id);
        if (optionalTable.isEmpty()) {
            throw new TableNotFoundException(id);
        }
        return optionalTable.get();
    }

    @Override
    @Transactional
    public List<RestaurantTable> createAll(Restaurant restaurant, List<CreateTableRequest> requests) {

        List<RestaurantTable> restaurantTables = new ArrayList<>();

        for (CreateTableRequest request : requests) {
            validateDuplicityOnTableIdentification(restaurant.getId(), request.getIdentification());
            restaurantTables.add(
                    this.tableRepository.saveAndFlush(RestaurantTable.fromCreateRequest(request, restaurant))
            );
        }

        return restaurantTables;
    }

    @Override
    public List<RestaurantTable> updateAll(List<UpdateTableRequest> requests, Restaurant restaurant) {

        for (UpdateTableRequest request : requests) {

            for (RestaurantTable restaurantTable : restaurant.getRestaurantTables()) {

                if (restaurantTable.getId().equals(request.getId())) {
                    Optional.ofNullable(request.getIdentification()).ifPresent(identification -> {
                        validateDuplicityOnTableIdentification(restaurant.getId(), request.getIdentification());
                        restaurantTable.setIdentification(identification);
                    });
                    Optional.ofNullable(request.getCapacity()).ifPresent(restaurantTable::setCapacity);
                    Optional.ofNullable(request.getStatus()).ifPresent(restaurantTable::setStatus);
                }
            }
        }

        return this.tableRepository.saveAll(restaurant.getRestaurantTables());
    }

    @Override
    public void updateStatus(RestaurantTable oldRestaurantTable, OnTableUpdateRequest request) {
        this.tableRepository.saveAndFlush(
                new RestaurantTable(oldRestaurantTable, request.getStatus())
        );
    }

    @Override
    public void deleteAll(List<UUID> tableIds) {
        this.tableRepository.deleteAll(
                this.tableRepository.findByIdIn(tableIds)
        );
    }

    private void validateDuplicityOnTableIdentification(UUID restaurantId, String identification) {
        if (this.tableRepository.existsByRestaurantIdAndIdentification(restaurantId, identification)) {
            throw new TableConflictException(identification);
        }
    }
}
