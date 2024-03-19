package com.automated.restaurant.automatedRestaurant.core.utils;

import lombok.NoArgsConstructor;

import java.util.concurrent.CompletableFuture;

public class AsyncUtils {

    public static <T> CompletableFuture<T> getCompletableFuture(T method) {
        return CompletableFuture.supplyAsync(() -> method);
    }
}
