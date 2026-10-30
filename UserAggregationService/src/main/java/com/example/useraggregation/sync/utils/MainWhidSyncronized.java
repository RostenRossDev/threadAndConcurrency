package com.example.useraggregation.sync.utils;

import com.example.useraggregation.sync.SynchronizedCounter;

public class MainWhidSyncronized {

    static void main() throws InterruptedException {
        run();
    }

    public static void run() throws InterruptedException {
        SynchronizedCounter counter = new SynchronizedCounter();
        Thread[] threads = new Thread[8];
        for (int i = 0; i < threads.length; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                for (long j = 0; j < 100_000_000_000_000L; j++) {
                    System.out.println(Thread.currentThread().getName() + " sincronyzed value: " + counter.get());
                    counter.increment();
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) thread.join();
        System.out.println("Esperado 800000; obtenido " + counter.get());
    }
}
