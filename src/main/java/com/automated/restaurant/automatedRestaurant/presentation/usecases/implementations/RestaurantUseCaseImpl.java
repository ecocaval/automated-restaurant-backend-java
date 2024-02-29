package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateRestaurantJobTitleRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateRestaurantRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateRestaurantTableRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateRestaurantRequest;
import com.automated.restaurant.automatedRestaurant.core.messages.ErrorMessages;
import com.automated.restaurant.automatedRestaurant.presentation.entities.JobTitle;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Product;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.entities.RestaurantTable;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.*;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.base.BadRequestException;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.RestaurantRepository;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.RestaurantTableRepository;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.ImageStoreUseCase;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.RestaurantUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RestaurantUseCaseImpl implements RestaurantUseCase {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    @Autowired
    private ImageStoreUseCase imageStoreUseCase;

    @Override
    public Restaurant findById(UUID id) {
        return this.restaurantRepository.findById(id)
                .orElseThrow(() -> new  RestaurantNotFoundException(id));
    }

    @Override
    public Restaurant create(CreateRestaurantRequest request) {
        return this.restaurantRepository.save(
                Restaurant.fromCreateRequest(request)
        );
    }

    @Override
    public Restaurant update(UpdateRestaurantRequest request, Restaurant restaurant) {

        updateRestaurantName(request, restaurant);

        return this.restaurantRepository.save(restaurant);
    }

    //    private void validateJobTitles(CreateRestaurantRequest request) {
//        List<String> requestedJobTitles = new ArrayList<>();
//
//        for (CreateRestaurantJobTitleRequest jobTitle : request.getJobTitles()) {
//            requestedJobTitles.forEach(title -> {
//                if (title.equals(jobTitle.getName())) {
//                    throw new DuplicatedJobTitleException(jobTitle.getName());
//                }
//            });
//            requestedJobTitles.add((jobTitle.getName()));
//        }
//    }
//
//    private void validateRestaurantTables(CreateRestaurantRequest request) {
//        List<String> requestedTableIdentification = new ArrayList<>();
//
//        for (CreateRestaurantTableRequest table : request.getTables()) {
//
//            requestedTableIdentification.forEach(identification -> {
//                if (identification.equals(table.getIdentification())) {
//                    throw new DuplicatedRestaurantTableException(table.getIdentification());
//                }
//            });
//            requestedTableIdentification.add((table.getIdentification()));
//        }
//    }

    private void validateDuplicityOnRestaurantTableIdentification(Restaurant restaurant, String identification) {

        var restaurantTableHasConflict = !restaurant.getTables().stream()
                .filter(table -> (table.getIdentification().equals(identification)))
                .toList()
                .isEmpty();

        if (restaurantTableHasConflict) throw new DuplicatedRestaurantTableException(identification);
    }

    private void validateDuplicityOnJobTitleName(Restaurant restaurant, String name) {

        var jobTitleNameIsInUse = !restaurant.getJobTitles().stream()
                .filter(jobTitle -> jobTitle.getName().equals(name))
                .toList()
                .isEmpty();

        if (jobTitleNameIsInUse) throw new DuplicatedJobTitleException(name);
    }

    private void validateDuplicityOnProductName(Restaurant restaurant, String name) {

        var productNameIsInUse = !restaurant.getProducts().stream()
                .filter(product -> product.getName().equals(name))
                .toList()
                .isEmpty();

        if (productNameIsInUse) throw new DuplicatedProductException(name);
    }

    private void updateRestaurantName(UpdateRestaurantRequest request, Restaurant restaurant) {
        if (request.getName() != null) {
            restaurant.setName(request.getName());
        }
    }
//
//    private void updateRestaurantJobTitles(UpdateRestaurantRequest request, Restaurant restaurant) {
//
//        if (request.getJobTitles() != null && !request.getJobTitles().isEmpty()) {
//
//            request.getJobTitles().forEach(jobTitleRequest -> {
//
//                if (jobTitleRequest.getOperation() == null) {
//                    throw new BadRequestException(ErrorMessages.ERROR_NULL_UPDATE_OPERATION.getMessage());
//                }
//
//                switch (jobTitleRequest.getOperation()) {
//                    case ADD -> {
//                        validateDuplicityOnJobTitleName(restaurant, jobTitleRequest.getName());
//
//                        restaurant.getJobTitles().add(
//                                new JobTitle(jobTitleRequest.getName(), restaurant)
//                        );
//                    }
//
//                    case DELETE -> restaurant.getJobTitles().removeIf(
//                            jobTitle -> jobTitle.getId().equals(jobTitleRequest.getId())
//                    );
//
//                    case UPDATE -> {
//                        validateDuplicityOnJobTitleName(restaurant, jobTitleRequest.getName());
//
//                        restaurant.getJobTitles().forEach(jobTitle -> {
//                            if (jobTitle.getId().equals(jobTitleRequest.getId())) {
//                                jobTitle.setName(jobTitleRequest.getName());
//                            }
//                        });
//                    }
//                }
//            });
//        }
//    }
//
//    private void updateRestaurantTables(UpdateRestaurantRequest request, Restaurant restaurant) {
//
//        if (request.getTables() != null && !request.getTables().isEmpty()) {
//
//            request.getTables().forEach(tableRequest -> {
//
//                if (tableRequest.getOperation() == null) {
//                    throw new BadRequestException(ErrorMessages.ERROR_NULL_UPDATE_OPERATION.getMessage());
//                }
//
//                switch (tableRequest.getOperation()) {
//                    case ADD -> {
//                        Optional.ofNullable(tableRequest.getIdentification()).orElseThrow(() ->
//                            new BadRequestException(ErrorMessages.ERROR_NULL_RESTAURANT_TABLE_IDENTIFICATION.getMessage())
//                        );
//
//                        Optional.ofNullable(tableRequest.getCapacity()).orElseThrow(() ->
//                            new BadRequestException(ErrorMessages.ERROR_NULL_RESTAURANT_TABLE_CAPACITY.getMessage())
//                        );
//
//                        validateDuplicityOnRestaurantTableIdentification(restaurant, tableRequest.getIdentification());
//
//                        restaurant.getTables().add(
//                            new RestaurantTable(tableRequest.getIdentification(), tableRequest.getCapacity(), restaurant)
//                        );
//                    }
//
//                    case DELETE -> restaurant.getTables().removeIf(
//                        table -> table.getId().equals(tableRequest.getId())
//                    );
//
//                    case UPDATE -> restaurant.getTables().forEach(table -> {
//                        if (table.getId().equals(tableRequest.getId())) {
//                            Optional.ofNullable(tableRequest.getIdentification()).ifPresent(identification -> {
//                                validateDuplicityOnRestaurantTableIdentification(restaurant, identification);
//                                table.setIdentification(identification);
//                            });
//                            Optional.ofNullable(tableRequest.getCapacity()).ifPresent(table::setCapacity);
//                            Optional.ofNullable(tableRequest.getStatus()).ifPresent(table::setStatus);
//                        }
//                    });
//                }
//            });
//        }
//    }
//
//    private void updateRestaurantProducts(UpdateRestaurantRequest request, Restaurant restaurant) {
//
//        if (request.getProducts() != null && !request.getProducts().isEmpty()) {
//
//            request.getProducts().forEach(productRequest -> {
//
//                if (productRequest.getOperation() == null) {
//                    throw new BadRequestException(ErrorMessages.ERROR_NULL_UPDATE_OPERATION.getMessage());
//                }
//
//                switch (productRequest.getOperation()) {
//                    case ADD -> {
//                        validateDuplicityOnProductName(productRequest.getName());
//
//                        restaurant.getProducts().add(
//                                new Product(jobTitleRequest.getName(), restaurant)
//                        );
//                    }
//
//                    case DELETE -> restaurant.getJobTitles().removeIf(
//                            jobTitle -> jobTitle.getId().equals(jobTitleRequest.getId())
//                    );
//
//                    case UPDATE -> {
//                        validateDuplicityOnJobTitleName(restaurant, jobTitleRequest.getName());
//
//                        restaurant.getJobTitles().forEach(jobTitle -> {
//                            if (jobTitle.getId().equals(jobTitleRequest.getId())) {
//                                jobTitle.setName(jobTitleRequest.getName());
//                            }
//                        });
//                    }
//                }
//            });
//        }
//    }
}
