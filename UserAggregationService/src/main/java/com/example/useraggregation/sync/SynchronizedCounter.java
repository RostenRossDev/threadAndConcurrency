package com.example.useraggregation.sync;

public final class SynchronizedCounter {
    private int value;

    // synchronized protege la operación compuesta leer-modificar-escribir.
    public synchronized void increment() {
        value++;
    }

    public synchronized int get() {
        return value;
    }

    public static void run() throws InterruptedException {
        SynchronizedCounter counter = new SynchronizedCounter();
        Thread[] threads = new Thread[8];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100_000; j++) counter.increment();
            });
            threads[i].start();
        }
        for (Thread thread : threads) thread.join();
        System.out.println("Esperado 800000; obtenido " + counter.get());
    }
}
