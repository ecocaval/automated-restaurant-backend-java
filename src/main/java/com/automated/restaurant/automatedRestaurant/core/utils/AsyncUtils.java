package com.automated.restaurant.automatedRestaurant.core.utils;

import java.util.concurrent.CompletableFuture;

public class AsyncUtils {

    public static <T> CompletableFuture<T> getCompletableFuture(T method) {
        return CompletableFuture.supplyAsync(() -> method);
    }

    @SafeVarargs
    public static <T extends CompletableFuture<?>> void completeFutures(T... futureReturn) {
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                futureReturn
        );
        allFutures.join();
    }
}
