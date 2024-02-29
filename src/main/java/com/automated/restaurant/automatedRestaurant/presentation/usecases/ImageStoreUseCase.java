package com.automated.restaurant.automatedRestaurant.presentation.usecases;

import com.automated.restaurant.automatedRestaurant.presentation.entities.Product;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.entities.RestaurantImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface ImageStoreUseCase {

    public RestaurantImage uploadRestaurantImage(MultipartFile file, Restaurant restaurant);

    public void uploadProductImage(MultipartFile file, Product product);

    public byte[] downloadRestaurantImage(UUID id);

    public  byte[] downloadProductImage(UUID id);
}
