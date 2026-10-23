package com.example.useraggregation.scheduling;

import java.util.concurrent.*;

public final class ScheduledTaskDemo {
    public static void run() throws InterruptedException {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        try {
            ScheduledFuture<?> periodic = scheduler.scheduleAtFixedRate(
                    () -> System.out.println("Pulso " + System.currentTimeMillis()),
                    0, 1, TimeUnit.SECONDS);
            Thread.sleep(2_200);
            periodic.cancel(false);
        } finally {
            scheduler.shutdown();
            if (!scheduler.awaitTermination(2, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        }
    }
}
