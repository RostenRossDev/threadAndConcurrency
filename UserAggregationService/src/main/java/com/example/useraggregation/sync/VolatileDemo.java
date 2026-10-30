package com.example.useraggregation.sync;

/** volatile comunica el estado, pero no hace atómico el operador contador++. */
public final class VolatileDemo {
    private volatile boolean running = true;

    public void stop() {
        System.out.println("en stop");
        running = false;
    }

    public void work() {
        while (running) {
            // En un programa real, el hilo haría trabajo útil.
            Thread.onSpinWait();
        }
        System.out.println("work terminó " + running);   // print AL SALIR, no dentro
    }
}
