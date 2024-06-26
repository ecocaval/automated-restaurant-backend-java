package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateTableRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateTableRequest;
import com.automated.restaurant.automatedRestaurant.core.data.responses.RestaurantTableResponse;
import com.automated.restaurant.automatedRestaurant.presentation.clients.SocketIoApiClient;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.entities.RestaurantTable;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.TableConflictException;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.TableNotFoundException;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.BadRequestException;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.ProductRepository;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.RestaurantTableRepository;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.RestaurantTableUseCase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RestaurantTableUseCaseImpl implements RestaurantTableUseCase {

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SocketIoApiClient socketIoApiClient;

    @Override
    public List<RestaurantTable> findAll() {
        return this.restaurantTableRepository.findAllByOrderByIdentificationAsc();
    }

    @Override
    public RestaurantTable findById(UUID id) {
        var optionalTable = this.restaurantTableRepository.findById(id);
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
            validateCreateTableRequest(request);
            validateDuplicityOnTableIdentificationOnCreation(restaurant.getId(), request.getIdentification());
            restaurantTables.add(
                    this.restaurantTableRepository.saveAndFlush(RestaurantTable.fromCreateRequest(request, restaurant))
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
                        validateDuplicityOnTableIdentificationOnUpdate(restaurant.getId(), request.getIdentification(), request.getId());
                        restaurantTable.setIdentification(identification);
                    });

                    Optional.ofNullable(request.getCapacity()).ifPresent(restaurantTable::setCapacity);

                    Optional.ofNullable(request.getStatus()).ifPresent(restaurantTable::setStatus);

                    Optional.ofNullable(request.getIsCallingWaiter()).ifPresent(restaurantTable::setCallingWaiter);

                    if (request.getStatus() != null || request.getIsCallingWaiter() != null) {
                        this.socketIoApiClient.publishTableEvent(
                                RestaurantTableResponse.fromRestaurantTable(restaurantTable),
                                String.valueOf(restaurantTable.getRestaurant().getId())
                        );
                    }
                }
            }
        }

        return this.restaurantTableRepository.saveAll(restaurant.getRestaurantTables());
    }

    @Override
    public void deleteAll(List<UUID> tableIds) {
        this.restaurantTableRepository.deleteAll(
                this.restaurantTableRepository.findByIdIn(tableIds)
        );
    }

    private void validateDuplicityOnTableIdentificationOnCreation(UUID restaurantId, String identification) {
        if (this.restaurantTableRepository.existsByRestaurantIdAndIdentification(restaurantId, identification)) {
            throw new TableConflictException(identification);
        }
    }

    private void validateDuplicityOnTableIdentificationOnUpdate(UUID restaurantId, String identification, UUID tableId) {
        if (this.restaurantTableRepository.existsByRestaurantIdAndIdentificationAndIdNotIn(restaurantId, identification, List.of(tableId))) {
            throw new TableConflictException(identification);
        }
    }

    private void validateCreateTableRequest(CreateTableRequest request) {
        if(request.getIdentification() == null) {
            throw new BadRequestException("A identifação da mesa precisa ser informada.");
        }
        if(request.getCapacity() == null) {
            throw new BadRequestException("A capacidade da mesa precisa ser informada.");
        }
    }
}
