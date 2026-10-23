package com.example.useraggregation.queue;

import java.util.concurrent.*;

public final class ProducerConsumerDemo {
    private static final int STOP = -1;

    public static void run() throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    int value = queue.take(); // bloquea si está vacía
                    if (value == STOP) break;
                    System.out.println("Consumido " + value);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        consumer.start();
        try {
            for (int i = 0; i < 10; i++) queue.put(i); // bloquea si está llena
            queue.put(STOP); // mensaje centinela para finalizar al consumidor
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        consumer.join();
    }
}
