# UserAggregationService — concurrencia en Java 25

Proyecto didáctico con clases separadas. No requiere `--enable-preview` ni ninguna característica preview.
Requiere JDK 25 y Maven.

## Ejecutar

```bash
mvn clean package
java -cp target/classes com.example.useraggregation.Main
```

Desde IntelliJ: abrir el `pom.xml`, configurar Project SDK en JDK 25 y ejecutar `Main`.

## Recorrido sugerido

1. `basics.ThreadAndRunnableDemo`: hilo, Runnable, join e interrupciones.
2. `basics.ExecutorFutureDemo`: ExecutorService, Callable y Future.
3. `basics.CompletableFutureDemo`: composición asíncrona y manejo de errores.
4. `sync.SynchronizedCounter`: carrera de datos y protección con synchronized.
5. `sync.AtomicCounterDemo`: AtomicInteger y LongAdder.
6. `sync.LockDemo`: ReentrantLock y ReadWriteLock.
7. `coordination.SemaphoreDemo`: límite de concurrencia.
8. `coordination.LatchBarrierPhaserDemo`: coordinación de fases.
9. `queue.ProducerConsumerDemo`: BlockingQueue y consumidores.
10. `collections.ConcurrentMapDemo`: ConcurrentHashMap y operaciones atómicas del mapa.
11. `context.ThreadLocalAndScopedValueDemo`: contexto por hilo y ScopedValue estable en Java 25.
12. `virtualthreads.VirtualThreadsDemo`: un hilo virtual por tarea.
13. `scheduling.ScheduledTaskDemo`: tareas periódicas.
14. `pitfalls.DeadlockDemo`: deadlock intencional y cómo evitarlo.
15. `pitfalls.WaitNotifyDemo`: wait/notifyAll correctamente bajo un monitor.
16. `parallel.ForkJoinAndStreamsDemo`: ForkJoinPool y parallel streams.
17. `service.UserAggregationService`: flujo integrado de agregación.

## Ideas fundamentales

- `volatile` garantiza visibilidad de una variable, pero no convierte `x++` en una operación atómica.
- `synchronized` protege una sección crítica y establece visibilidad entre entradas/salidas del mismo monitor.
- `AtomicInteger` sirve para operaciones atómicas sobre un valor; no hace atómica una transacción que modifica varios objetos.
- `LongAdder` es útil para estadísticas con mucha contención. `sum()` no representa necesariamente una instantánea atómica.
- Un `Semaphore` limita cuántas tareas poseen permisos al mismo tiempo. Siempre liberar el permiso en `finally`.
- `ExecutorService` debe cerrarse. En Java moderno, `try` con `ExecutorService` es válido porque implementa `AutoCloseable`.
- Interrumpir un hilo es una solicitud cooperativa. El código debe respetar `InterruptedException` o restaurar el estado con `Thread.currentThread().interrupt()`.
- Hilos virtuales ayudan especialmente con muchas tareas bloqueantes; no vuelven más rápida automáticamente una tarea intensiva en CPU.
- Evitá mezclar mecanismos por moda. Elegí el mecanismo más simple que preserve la invariantes de tu aplicación.
