package com.example.useraggregation.context;

public final class ThreadLocalAndScopedValueDemo {
    private static final ThreadLocal<String> USER_PER_THREAD = new ThreadLocal<>();

    public static void run() {
        USER_PER_THREAD.set("usuario-del-hilo");
        try {
            System.out.println("ThreadLocal: " + USER_PER_THREAD.get());

            ScopedValue.where(RequestContext.REQUEST_ID, "req-demo").run(() -> {
                System.out.println("ScopedValue: " + RequestContext.REQUEST_ID.get());
            });

            System.out.println("¿Hay binding afuera? "
                    + RequestContext.REQUEST_ID.isBound());
        } finally {
            // En pools, olvidar remove() puede filtrar datos entre tareas.
            USER_PER_THREAD.remove();
        }
    }
}
