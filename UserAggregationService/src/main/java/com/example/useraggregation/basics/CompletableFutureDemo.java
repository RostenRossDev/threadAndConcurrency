package com.example.useraggregation.basics;

import java.util.concurrent.*;

public final class CompletableFutureDemo {

    static void main() {
        run();
    }

    public static void run() {

        try (ExecutorService deadpool = Executors.newFixedThreadPool(3)) {

            TareaConSupplier tarea = new TareaConSupplier();
            CompletableFuture<Integer> price = CompletableFuture.supplyAsync(tarea, deadpool);
            CompletableFuture<Integer> shipping = CompletableFuture.supplyAsync(() -> 15, deadpool);
            int total = price.thenCombine(shipping, Integer::sum).join();
            System.out.println("Total: " + total);

            String safe = CompletableFuture.<String>supplyAsync(
                            () -> {
                                throw new IllegalStateException("fallo");
                            }, deadpool)

                    .exceptionally(ex -> "valor alternativo: " + ex.getClass().getSimpleName())
                    .join();
            System.out.println(safe);
        }
    }
}
