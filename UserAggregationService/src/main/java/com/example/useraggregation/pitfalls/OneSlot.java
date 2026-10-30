package com.example.useraggregation.pitfalls;

public final class OneSlot {

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