package com.example.useraggregation.basics;

public final class ThreadAndRunnableDemo {

    static void main() throws InterruptedException {
        run();
    }
    public static void run() throws InterruptedException {
//        Runnable work = () -> System.out.println("Ejecutando en " + Thread.currentThread());
        String threadName = "worker-manual";
        Runnable work = new Tarea(threadName);
        Thread worker = new Thread(work, threadName);
        worker.start(); // start crea ejecución concurrente; run() directo no lo hace.
        worker.join();  // el hilo actual espera a que worker termine.

        Thread interruptible = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                // sleep limpia el flag al lanzar la excepción: restaurarlo si se sale.
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                Thread.currentThread().interrupt();
                System.out.println("Hilo interrumpido correctamente[: " + Thread.currentThread().getName());
            }
        }, "interruptible");
        interruptible.start();
        Thread.sleep(250);
        System.out.println("Interrumpimos ");
        long start = System.nanoTime();
        interruptible.interrupt(); // solicitud cooperativa de cancelación
        interruptible.join();
        System.out.println("Tiempo que espero el hilo principal para retomar la ejecucion: " + ((System.nanoTime() - start) / 1_000_000.0));
    }
}
