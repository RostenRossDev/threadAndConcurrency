package com.example.useraggregation.coordination;

import java.util.concurrent.*;

public final class LatchBarrierPhaserDemo {


    static void main() throws InterruptedException {
        run();
    }
    public static void run() throws InterruptedException {

        //###################################################################################################
        // CountDownLatch: un coordinador espera a que terminen N tareas.
        CountDownLatch done = new CountDownLatch(2);

        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> {
                try {
                    Thread.sleep(1000L * (id + 1));
                    System.out.println("Terminó tarea " + id);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    System.out.println("Cuenta: " + done.getCount());
                    done.countDown();
                }
            }).start();
        }

        done.await();


        System.out.println("Todas terminaron");

        //###################################################################################################
        // CyclicBarrier: cada participante espera a que lleguen todos.

        CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("Los tres llegaron a la barrera"));

        Thread[] group = new Thread[3];

        for (int i = 0; i < group.length; i++) {
            group[i] = new Thread( new TareaConCicleBarrier(barrier));
            group[i].start();
        }

        for (Thread thread : group) thread.join();



        //###################################################################################################
        // Phaser: fases dinámicas; cada participante registra y avanza.
        Phaser phaser = new Phaser(1); // el hilo principal es un participante
        for (int i = 0; i < 2; i++) {
            phaser.register();  // Incrementa en 1 el contador de las partes, en nuestro caso tenemos 1 asi que lo incremetaria a 2 y lueg a 3 xq se ejecuta en bucle que se repite 2 veces.
            new Thread(() -> {
                System.out.println("Fase de preparación");
                phaser.arriveAndAwaitAdvance();
                System.out.println("Fase de ejecución");
                phaser.arriveAndDeregister();
            }).start();
        }
        //En este punto los participantes del phaser son 3 xq incrementamos 2 veces al llamar a phaser.register() 2 veces.
        phaser.arriveAndAwaitAdvance();
        phaser.arriveAndDeregister();

    }
}
