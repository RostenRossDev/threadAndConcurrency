package com.example.useraggregation.pitfalls;

public final class DeadlockDemo {
    public static void run() throws InterruptedException {
        Object lockA = new Object();
        Object lockB = new Object();

        Thread first = new Thread(() -> {
            synchronized (lockA) {
                pause();
                synchronized (lockB) {
                    System.out.println("Primero obtuvo ambos");
                }
            }
        }, "first");

        Thread second = new Thread(() -> {
            synchronized (lockB) {
                pause();
                synchronized (lockA) {
                    System.out.println("Segundo obtuvo ambos");
                }
            }
        }, "second");

        first.start();
        second.start();
        first.join(500);
        second.join(500);
        System.out.println("Estados: " + first.getState() + ", " + second.getState());
        System.out.println("Si ambos están BLOCKED, hay deadlock. Este ejemplo puede dejar hilos vivos.");
        // Prevención: adquirir los dos locks siempre en el mismo orden.
    }

    private static void pause() {
        try { Thread.sleep(100); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
