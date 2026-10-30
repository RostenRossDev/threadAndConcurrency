package com.example.useraggregation.pitfalls;

/** Demuestra wait/notifyAll. En código normal, preferí BlockingQueue si encaja. */
public final class WaitNotifyDemo {

    static void main() throws InterruptedException {
        run();
    }

    public static void run() throws InterruptedException {
        OneSlot slot = new OneSlot();

        // Consumidor PRIMERO (main): se bloqueará en wait() porque full=false
        Thread consumer = new Thread(() -> {
            try {
                System.out.println("Recibido: " + slot.take());  // ← esperará
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        consumer.start();

        Thread.sleep(30000);   // ← damos tiempo a que el consumidor entre en wait()

        Thread producer = new Thread(() -> {
            try { slot.put("mensaje"); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        producer.start();
        System.out.println("se creo el produce que setea la variable");
        consumer.join();
        producer.join();
    }
}
