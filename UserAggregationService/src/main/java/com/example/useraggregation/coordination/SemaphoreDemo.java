package com.example.useraggregation.coordination;

import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;

public final class SemaphoreDemo {

    static void main() throws InterruptedException {
        run();
    }

    public static void run() throws InterruptedException {

        // Como máximo dos tareas pueden estar dentro de la sección limitada.
        Semaphore permits = new Semaphore(2, true);

        Runnable task = new TareaConSemaforo(permits);

        Thread a = new Thread(task, "A");
        Thread b = new Thread(task, "B");
        Thread c = new Thread(task, "C");
        a.start();
        b.start();
        c.start();
        a.join();
        System.out.println("Termino A: " + LocalDateTime.now());
        b.join();
        System.out.println("Termino B: " + LocalDateTime.now());
        c.join();
        System.out.println("Termino C: " + LocalDateTime.now());

    }
}
