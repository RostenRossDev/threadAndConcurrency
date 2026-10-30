package com.example.useraggregation.sync;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public final class AtomicCounterDemo {

    static void main() throws InterruptedException {
        run();
    }

    public static void run() throws InterruptedException {
        AtomicInteger exactCounter = new AtomicInteger();
        LongAdder highContentionMetric = new LongAdder();
        Thread[] threads = new Thread[8];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100_000; j++) {
                    System.out.println("Hilo: " + Thread.currentThread().getName() + " valor: " + exactCounter.incrementAndGet());
                    highContentionMetric.increment();
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) thread.join();
        System.out.println("AtomicInteger = " + exactCounter.get());
        System.out.println("LongAdder = " + highContentionMetric.sum());
    }
}
