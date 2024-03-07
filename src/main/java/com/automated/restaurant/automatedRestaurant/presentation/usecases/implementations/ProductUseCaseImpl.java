package com.automated.restaurant.automatedRestaurant.presentation.usecases.implementations;

import com.automated.restaurant.automatedRestaurant.core.data.requests.CreateProductRequest;
import com.automated.restaurant.automatedRestaurant.core.data.requests.UpdateProductRequest;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Product;
import com.automated.restaurant.automatedRestaurant.presentation.entities.Restaurant;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.ProductConflictException;
import com.automated.restaurant.automatedRestaurant.presentation.exceptions.ProductNotFoundException;
import com.automated.restaurant.automatedRestaurant.presentation.repositories.ProductRepository;
import com.automated.restaurant.automatedRestaurant.presentation.usecases.ProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

            var product = Product.fromCreateRequest(request, restaurant);

            validateProductDuplicity(product);

            products.add(
                    this.productRepository.save(product)
            );
        }

        return products;
    }

    @Override
    public List<Product> updateAll(List<UpdateProductRequest> requests, Restaurant restaurant) {

        Set<Product> updatedProducts = new HashSet<>();

        for (UpdateProductRequest request : requests) {

            for (Product product : restaurant.getProducts()) {

                if (!product.getId().equals(request.getId())) {
                    continue;
                }

                Optional.ofNullable(request.getName()).ifPresent(product::setName);
                Optional.ofNullable(request.getDescription()).ifPresent(product::setDescription);
                Optional.ofNullable(request.getPrice()).ifPresent(product::setPrice);
                Optional.ofNullable(request.getServingCapacity()).ifPresent(product::setServingCapacity);
                Optional.ofNullable(request.getSku()).ifPresent(product::setSku);
                Optional.ofNullable(request.getActive()).ifPresent(product::setActive);

                updatedProducts.add(product);

                validateProductDuplicity(product);
            }
        }

        return this.productRepository.saveAll(updatedProducts);
    }

    @Override
    public void deleteAll(List<UUID> productIds) {
        this.productRepository.deleteAll(
                this.productRepository.findByIdIn(productIds)
        );
    }

    private void validateProductDuplicity(Product product) {

        if(product.getId() == null) {
            return;
        }

        if (productRepository.existsByRestaurantIdAndNameAndSkuAndServingCapacityAndIdNotIn(
                product.getRestaurant().getId(),
                product.getName(),
                product.getSku(),
                product.getServingCapacity(),
                List.of(product.getId())
        )) {
            throw new ProductConflictException(
                    product.getRestaurant().getId(), product.getName(), product.getSku(), product.getServingCapacity()
            );
        }
    }
}
