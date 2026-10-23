package com.example.useraggregation.basics;

import java.util.concurrent.*;

public final class ExecutorFutureDemo {
    public static void run() throws Exception {
        try (ExecutorService pool = Executors.newFixedThreadPool(3)) {
            Callable<Integer> calculation = () -> {
                Thread.sleep(100);
                return 6 * 7;
            };
            Future<Integer> future = pool.submit(calculation);
            System.out.println("Resultado: " + future.get()); // bloquea hasta tener resultado
            System.out.println("Cancelada: " + future.isCancelled());
        }
    }
}
