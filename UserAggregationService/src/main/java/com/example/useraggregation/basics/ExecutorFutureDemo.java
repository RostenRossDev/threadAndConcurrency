package com.example.useraggregation.basics;

import java.util.concurrent.*;

public final class ExecutorFutureDemo {

    static void main() throws Exception {
        run();
    }

    public static void run() throws Exception {
        try (ExecutorService deadpool = Executors.newFixedThreadPool(5)) {
            Callable<Integer> calculation = () -> {   // --> Creo la tarea que retorna la multiplicacion de 6 * 7
                Thread.sleep(10000);
                return 6 * 7;
            };

            long start = System.nanoTime();
            Future<Integer> futureCalculation = deadpool.submit(calculation);  // --> Mando a ejecutar la tarea al primer hilo que se libere de nuestro thread pool o picina de hilos.

            System.out.println("Resultado: " + futureCalculation.get()); // bloquea el hilo que invoca al .get() ( en este caso al hilo principal ) hasta tener resultado
            System.out.println("Tiempo que espero el hilo principal para retomar la ejecucion: " + ((System.nanoTime() - start) / 1_000_000.0));
            System.out.println("Cancelada: " + futureCalculation.isCancelled());
        }
    }
}
