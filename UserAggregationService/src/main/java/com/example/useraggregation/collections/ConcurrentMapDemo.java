package com.example.useraggregation.collections;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;

public final class ConcurrentMapDemo {
    public static void run() throws InterruptedException {
        ConcurrentMap<String, LongAdder> visits = new ConcurrentHashMap<>();
        Thread[] threads = new Thread[4];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10_000; j++) {
                    // computeIfAbsent asegura que la celda se cree de forma segura.
                    visits.computeIfAbsent("/users", ignored -> new LongAdder()).increment();
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) thread.join();
        System.out.println("Visitas: " + visits.get("/users").sum());
    }
}
