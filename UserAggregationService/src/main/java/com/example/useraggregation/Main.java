package com.example.useraggregation;

import com.example.useraggregation.service.UserAggregationService;
import com.example.useraggregation.service.UserSource;
import com.example.useraggregation.model.User;
import com.example.useraggregation.basics.*;
import com.example.useraggregation.sync.*;
import com.example.useraggregation.coordination.*;
import com.example.useraggregation.queue.ProducerConsumerDemo;
import com.example.useraggregation.collections.ConcurrentMapDemo;
import com.example.useraggregation.context.ThreadLocalAndScopedValueDemo;
import com.example.useraggregation.virtualthreads.VirtualThreadsDemo;
import com.example.useraggregation.scheduling.ScheduledTaskDemo;
import com.example.useraggregation.pitfalls.*;
import com.example.useraggregation.parallel.ForkJoinAndStreamsDemo;

import java.util.List;

/**
 * Punto de entrada. Por defecto ejecuta el servicio integrado.
 * Para estudiar un tema puntual, descomentá una demo por vez.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("=== USER AGGREGATION SERVICE ===");
        UserAggregationService service = new UserAggregationService(new UserSource());
        List<User> users = service.aggregate(List.of(1, 2, 3, 4, 5, 6, 7, 8));
        users.forEach(System.out::println);
        System.out.println(service.metrics().summary());
        service.close();

        // Descomentá una demo para estudiar cada concepto:
        // ThreadAndRunnableDemo.run();
        // ExecutorFutureDemo.run();
        // CompletableFutureDemo.run();
        // SynchronizedCounter.run();
        // AtomicCounterDemo.run();
        // LockDemo.run();
        // SemaphoreDemo.run();
        // LatchBarrierPhaserDemo.run();
        // ProducerConsumerDemo.run();
        // ConcurrentMapDemo.run();
        // ThreadLocalAndScopedValueDemo.run();
        // VirtualThreadsDemo.run();
        // ScheduledTaskDemo.run();
        // DeadlockDemo.run(); // tiene un deadlock intencional; usar solo para estudiar
        // WaitNotifyDemo.run();
        // ForkJoinAndStreamsDemo.run();
    }
}
