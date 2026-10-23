package com.example.useraggregation.coordination;

import java.util.concurrent.*;

public final class LatchBarrierPhaserDemo {
    public static void run() throws InterruptedException {
        // CountDownLatch: un coordinador espera a que terminen N tareas.
        CountDownLatch done = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> {
                try {
                    Thread.sleep(100L * (id + 1));
                    System.out.println("Terminó tarea " + id);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();
                }
            }).start();
        }
        done.await();
        System.out.println("Todas terminaron");

        // CyclicBarrier: cada participante espera a que lleguen todos.
        CyclicBarrier barrier = new CyclicBarrier(3,
                () -> System.out.println("Los tres llegaron a la barrera"));
        Thread[] group = new Thread[3];
        for (int i = 0; i < group.length; i++) {
            group[i] = new Thread(() -> {
                try { barrier.await(); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                catch (BrokenBarrierException e) { throw new RuntimeException(e); }
            });
            group[i].start();
        }
        for (Thread thread : group) thread.join();

        // Phaser: fases dinámicas; cada participante registra y avanza.
        Phaser phaser = new Phaser(1); // el hilo principal es un participante
        for (int i = 0; i < 2; i++) {
            phaser.register();
            new Thread(() -> {
                System.out.println("Fase de preparación");
                phaser.arriveAndAwaitAdvance();
                System.out.println("Fase de ejecución");
                phaser.arriveAndDeregister();
            }).start();
        }
        phaser.arriveAndAwaitAdvance();
        phaser.arriveAndDeregister();
    }
}
