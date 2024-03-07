package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.automated.restaurant.automatedRestaurant.core.utils.ImageUtils;
import com.automated.restaurant.automatedRestaurant.presentation.entities.*;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.ImageNotFoundException;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.ImageStoringException;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.FileRepository;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.RestaurantImageRepository;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.ImageStoreUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageStoreUseCaseImpl implements ImageStoreUseCase {

    @Autowired
    private RestaurantImageRepository restaurantImageRepository;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public FileEntity uploadRestaurantImage(MultipartFile file, Restaurant restaurant) {
        try {
            ImageUtils.validateImageType(file.getContentType());

            return restaurantImageRepository.save(
                    FileEntity.builder()
                            .name(file.getOriginalFilename())
                            .type(file.getContentType())
                            .imageData(file.getBytes()))
                            .build()
            );
        } catch (IOException ex) {
            throw new ImageStoringException();
        }
    }

    @Override
    public void uploadProductImage(MultipartFile file, Product product) {
        try {
            ImageUtils.validateImageType(file.getContentType());

            fileRepository.save(
                    ProductFile.builder()
                            .name(file.getOriginalFilename())
                            .type(file.getContentType())
                            .imageData(ImageUtils.compressImage(file.getBytes()))
                            .product(product)
                            .build()
            );
        } catch (IOException ex) {
            throw new ImageStoringException();
        }
    }

    @Override
    public byte[] downloadRestaurantImage(UUID id) {
        return ImageUtils.decompressImage(
                this.restaurantImageRepository.findById(id)
                        .orElseThrow(() -> new ImageNotFoundException(id))
                        .getImageData()
        );
    }

    @Override
    public ProductFile downloadProductImage(UUID id) {
        return this.fileRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException(id));
    }
}
