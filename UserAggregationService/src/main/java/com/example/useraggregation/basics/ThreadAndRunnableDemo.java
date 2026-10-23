package com.example.useraggregation.basics;

public final class ThreadAndRunnableDemo {
    public static void run() throws InterruptedException {
        Runnable work = () -> System.out.println("Ejecutando en " + Thread.currentThread());

        Thread worker = new Thread(work, "worker-manual");
        worker.start(); // start crea ejecución concurrente; run() directo no lo hace.
        worker.join();  // el hilo actual espera a que worker termine.

        Thread interruptible = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                // sleep limpia el flag al lanzar la excepción: restaurarlo si se sale.
                Thread.currentThread().interrupt();
                System.out.println("Hilo interrumpido correctamente");
            }
        }, "interruptible");
        interruptible.start();
        Thread.sleep(250);
        interruptible.interrupt(); // solicitud cooperativa de cancelación
        interruptible.join();
    }
}
