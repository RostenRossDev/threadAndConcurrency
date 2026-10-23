package com.example.useraggregation.basics;

import java.util.concurrent.*;

public final class CompletableFutureDemo {
    public static void run() {
        try (ExecutorService pool = Executors.newFixedThreadPool(3)) {
            CompletableFuture<Integer> price = CompletableFuture.supplyAsync(() -> 100, pool);
            CompletableFuture<Integer> shipping = CompletableFuture.supplyAsync(() -> 15, pool);

            int total = price.thenCombine(shipping, Integer::sum).join();
            System.out.println("Total: " + total);

            String safe = CompletableFuture.<String>supplyAsync(
                            () -> { throw new IllegalStateException("fallo"); }, pool)
                    .exceptionally(ex -> "valor alternativo: " + ex.getClass().getSimpleName())
                    .join();
            System.out.println(safe);
        }
    }
}
