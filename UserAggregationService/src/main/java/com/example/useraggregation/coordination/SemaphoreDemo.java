package com.example.useraggregation.coordination;

import java.util.concurrent.Semaphore;

public final class SemaphoreDemo {
    public static void run() throws InterruptedException {
        // Como máximo dos tareas pueden estar dentro de la sección limitada.
        Semaphore permits = new Semaphore(2, true);
        Runnable task = () -> {
            boolean acquired = false;
            try {
                permits.acquire();
                acquired = true;
                System.out.println("Entró: " + Thread.currentThread().getName());
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                if (acquired) permits.release();
            }
        };
        Thread a = new Thread(task, "A");
        Thread b = new Thread(task, "B");
        Thread c = new Thread(task, "C");
        a.start(); b.start(); c.start();
        a.join(); b.join(); c.join();
    }
}
