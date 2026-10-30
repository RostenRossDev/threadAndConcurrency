package com.example.useraggregation.sync.utils;

import com.example.useraggregation.sync.VolatileDemo;

public class MainVolatileDemo {

    static void main() throws InterruptedException {
        VolatileDemo volatileDemo = new VolatileDemo();

        Thread worker = new Thread(volatileDemo::work, "worker");   // HILO B
        System.out.println("llamamos a worker.start");
        worker.start();
        // ... el hilo principal sigue ...

//        System.out.println("dormimos el hilo main 3s");
//        Thread.sleep(3000l);

        System.out.println("alive antes de stop = " + worker.isAlive());

        volatileDemo.stop();

        worker.join();
        System.out.println("alive despues de stop = " + worker.isAlive());  // ← querés ver "true" si falla

    }
}
