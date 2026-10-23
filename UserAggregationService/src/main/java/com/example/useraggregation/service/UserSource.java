package com.example.useraggregation.service;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Simula dos servicios externos lentos.
 * ThreadLocalRandom es apropiado para generar valores aleatorios concurrentemente.
 */
public class UserSource {
    public String fetchName(int id) throws InterruptedException {
        simulateLatency();
        return switch (id % 4) {
            case 0 -> "Ana";
            case 1 -> "Juan";
            case 2 -> "Lucía";
            default -> "Diego";
        } + "-" + id;
    }

    public String fetchEmail(int id) throws InterruptedException {
        simulateLatency();
        return "usuario" + id + "@example.com";
    }

    public String fetchDepartment(int id) throws InterruptedException {
        simulateLatency();
        return switch (id % 3) {
            case 0 -> "Ingeniería";
            case 1 -> "Datos";
            default -> "Producto";
        };
    }

    private void simulateLatency() throws InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextInt(80, 221));
        // Para que el ejemplo principal sea reproducible, esta fuente no falla al azar.
        // Los fallos y su recuperación se estudian en CompletableFutureDemo.
    }
}
