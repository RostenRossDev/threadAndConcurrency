package com.example.useraggregation.coordination;

import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;

public class TareaConSemaforo implements Runnable {
    Semaphore permits;


    public TareaConSemaforo(Semaphore permits){
        this.permits = permits;
    }

    @Override
    public void run() {
        boolean acquired = false;
        try {
            System.out.println(Thread.currentThread().getName() + ": Antes de solicitura el recurso/seccion critica");
            permits.acquire();
            acquired = true;
            System.out.println("Entró: " + Thread.currentThread().getName());
            Thread.sleep(300);
            System.out.println(Thread.currentThread().getName() + ": " + LocalDateTime.now());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (acquired) permits.release();
        }
    }
}
