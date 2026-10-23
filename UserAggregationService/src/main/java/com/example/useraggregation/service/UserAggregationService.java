package com.example.useraggregation.service;

import com.example.useraggregation.context.RequestContext;
import com.example.useraggregation.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Ejemplo integrado:
 * - CompletableFuture para componer las consultas.
 * - ExecutorService con hilos virtuales para I/O bloqueante.
 * - Semaphore para limitar la concurrencia contra servicios externos.
 * - ConcurrentHashMap para una caché compartida.
 * - Atomic/LongAdder en Metrics.
 * - ScopedValue para propagar un identificador de petición en el ámbito actual.
 */
public class UserAggregationService implements AutoCloseable {
    private final UserSource source;
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private final Semaphore externalCallSlots = new Semaphore(4, true);
    private final ConcurrentMap<Integer, User> cache = new ConcurrentHashMap<>();
    private final Metrics metrics = new Metrics();

    public UserAggregationService(UserSource source) {
        this.source = source;
    }

    public Metrics metrics() {
        return metrics;
    }

    public List<User> aggregate(List<Integer> ids) {
        String requestId = "req-" + System.nanoTime();

        // ScopedValue es estable desde Java 25. El binding se limita a este ámbito.
        return RequestContext.withRequestId(requestId, () -> {
            List<CompletableFuture<User>> futures = new ArrayList<>();
            for (int id : ids) {
                // Un ExecutorService común no propaga automáticamente ScopedValue.
                // Pasamos requestId explícitamente a cada tarea asíncrona.
                futures.add(CompletableFuture.supplyAsync(() -> loadUser(id, requestId), executor));
            }

            // allOf espera a que terminen todas. Si una falla, join propaga la excepción.
            CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();

            return futures.stream().map(CompletableFuture::join).toList();
        });
    }

    private User loadUser(int id, String requestId) {
        User cached = cache.get(id);
        if (cached != null) {
            return cached;
        }

        metrics.taskStarted();
        long startedAt = System.nanoTime();
        try {
            // Cada llamada externa adquiere un permiso y siempre lo libera.
            String name = callExternal(() -> source.fetchName(id));
            String email = callExternal(() -> source.fetchEmail(id));
            String department = callExternal(() -> source.fetchDepartment(id));
            User result = new User(id, name, email, department, requestId);
            // Evita reemplazos inconsistentes en una carrera de caché.
            User previous = cache.putIfAbsent(id, result);
            metrics.taskSucceeded(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startedAt));
            return previous != null ? previous : result;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            metrics.taskFailed();
            throw new CompletionException("Tarea interrumpida para usuario " + id, e);
        } catch (Exception e) {
            metrics.taskFailed();
            throw new CompletionException("No se pudo agregar usuario " + id, e);
        }
    }

    private <T> T callExternal(InterruptibleSupplier<T> operation) throws Exception {
        externalCallSlots.acquire();
        try {
            return operation.get();
        } finally {
            externalCallSlots.release();
        }
    }

    @Override
    public void close() {
        // close() del ExecutorService realiza apagado ordenado en Java moderno.
        executor.close();
    }

    @FunctionalInterface
    private interface InterruptibleSupplier<T> {
        T get() throws Exception;
    }
}
