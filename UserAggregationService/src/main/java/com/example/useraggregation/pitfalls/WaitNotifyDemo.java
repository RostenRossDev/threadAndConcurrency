package com.example.useraggregation.pitfalls;

/** Demuestra wait/notifyAll. En código normal, preferí BlockingQueue si encaja. */
public final class WaitNotifyDemo {
    private static final class OneSlot {
        private String value;
        private boolean full;

        public synchronized void put(String next) throws InterruptedException {
            while (full) wait(); // siempre while: puede haber despertares espurios
            value = next;
            full = true;
            notifyAll();
        }

        public synchronized String take() throws InterruptedException {
            while (!full) wait();
            String result = value;
            value = null;
            full = false;
            notifyAll();
            return result;
        }
    }

    public static void run() throws InterruptedException {
        OneSlot slot = new OneSlot();
        Thread producer = new Thread(() -> {
            try { slot.put("mensaje"); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        producer.start();
        System.out.println("Recibido: " + slot.take());
        producer.join();
    }
}
