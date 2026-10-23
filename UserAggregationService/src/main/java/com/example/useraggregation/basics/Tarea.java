package com.example.useraggregation.basics;

public class Tarea implements Runnable{

    private Long createdAt;
    private Long start;
    private String threadName;

    public Tarea(String threadName) {
        this.createdAt = System.nanoTime();
        this.threadName = threadName;
    }

    @Override
    public void run() {
        this.start = System.nanoTime();
        System.out.println("Nombre del hilo recibido por constructor: " + threadName);
        System.out.println("Nombre sacado directamente del hilo en ejecuccion: " + Thread.currentThread().getName());

        long waitTime = start - createdAt;
        double wait = waitTime / 1_000_000.0;
        long duration = System.nanoTime() - start;
        double millis = duration / 1_000_000.0;    // 1500.0 ms
        double seconds = duration / 1_000_000_000.0; // 1.5 s
        IO.println(String.format("La tarea espero %.2f mili para ser ejecutada y se ejecuto en %.2f mili", wait, millis));
    }
}
