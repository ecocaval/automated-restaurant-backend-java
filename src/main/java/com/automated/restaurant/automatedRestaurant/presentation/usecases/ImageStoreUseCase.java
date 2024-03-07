package com.automated.restaurant.automatedRestaurant.presentation.usecases;

import com.automated.restaurant.automatedRestaurant.presentation.entities.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ImageStoreUseCase {

    public FileEntity uploadRestaurantImage(MultipartFile file, Restaurant restaurant);

    public void uploadProductImage(MultipartFile file, Product product);

    public byte[] downloadRestaurantImage(UUID id);

    public FileEntity downloadProductImage(UUID id);
}
