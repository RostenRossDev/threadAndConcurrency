package com.example.useraggregation.virtualthreads;

import java.util.concurrent.*;

public final class VirtualThreadsDemo {
    public static void run() throws InterruptedException {
        // Un hilo virtual por tarea: apropiado para muchas tareas bloqueantes.
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 100; i++) {
                int id = i;
                executor.submit(() -> {
                    try {
                        Thread.sleep(20);
                        if (id < 3) {
                            System.out.println("Tarea " + id + " en " + Thread.currentThread());
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
        } // espera el cierre ordenado y las tareas ya enviadas
    }
}
