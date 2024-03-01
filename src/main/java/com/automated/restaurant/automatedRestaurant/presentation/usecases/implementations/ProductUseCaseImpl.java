package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateProductRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateProductRequest;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Product;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.ProductNotFoundException;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.ProductRepository;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.ProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductUseCaseImpl implements ProductUseCase {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product findById(UUID id) {
        return this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public List<Product> createAll(Restaurant restaurant, List<CreateProductRequest> requests) {
        List<Product> products = new ArrayList<>();

        for (CreateProductRequest request : requests) {
            products.add(
                    this.productRepository.save(Product.fromCreateRequest(request, restaurant))
            );
        }

        return products;
    }

    @Override
    public List<Product> updateAll(List<UpdateProductRequest> requests, Restaurant restaurant) {

        for (UpdateProductRequest request : requests) {

            for (Product product : restaurant.getProducts()) {

                if (product.getId().equals(request.getId())) {
                    Optional.ofNullable(request.getName()).ifPresent(product::setName);
                    Optional.ofNullable(request.getDescription()).ifPresent(product::setDescription);
                    Optional.ofNullable(request.getPrice()).ifPresent(product::setPrice);
                }
            }
        }

        return this.productRepository.saveAll(restaurant.getProducts());
    }

    @Override
    public void deleteAll(List<UUID> productIds) {
        this.productRepository.deleteAll(
                this.productRepository.findByIdIn(productIds)
        );
    }
}
