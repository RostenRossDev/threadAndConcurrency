package com.example.useraggregation.sync;

public final class SynchronizedCounter {

    private long value;

    // synchronized protege la operación compuesta leer-modificar-escribir.
    public synchronized void increment() {
        value++;
    }

    public synchronized Long get() {
        return value;
    }
}
