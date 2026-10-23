package com.example.useraggregation.service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * Estadísticas concurrentes: no requiere synchronized para cada incremento.
 */
public class Metrics {
    private final AtomicInteger started = new AtomicInteger();
    private final AtomicInteger succeeded = new AtomicInteger();
    private final AtomicInteger failed = new AtomicInteger();
    private final LongAdder totalLatencyMillis = new LongAdder();

    public void taskStarted() {
        started.incrementAndGet();
    }

    public void taskSucceeded(long latencyMillis) {
        succeeded.incrementAndGet();
        totalLatencyMillis.add(latencyMillis);
    }

    public void taskFailed() {
        failed.incrementAndGet();
    }

    public String summary() {
        long count = succeeded.get(); // AtomicInteger: get()
        double average = count == 0 ? 0 : (double) totalLatencyMillis.sum() / count;
        return "Métricas: iniciadas=" + started.get()
                + ", exitosas=" + succeeded.get()
                + ", fallidas=" + failed.get()
                + ", latencia media exitosa=" + String.format("%.1f ms", average);
    }
}
