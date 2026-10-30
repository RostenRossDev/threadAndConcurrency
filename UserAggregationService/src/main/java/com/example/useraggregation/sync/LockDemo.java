package com.example.useraggregation.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class LockDemo {
    private final Lock lock = new ReentrantLock();
    private int balance;
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private String configuration = "v1";

    public void deposit(int amount) {
        lock.lock();
        try {
            balance += amount;
        } finally {
            lock.unlock(); // indispensable incluso si la operación lanza excepción
        }
    }

    public int balance() {
        lock.lock();
        try { return balance; }
        finally { lock.unlock(); }
    }

    public String readConfiguration() {
        rwLock.readLock().lock();
        try { return configuration; }
        finally { rwLock.readLock().unlock(); }
    }

    public void updateConfiguration(String newValue) {
        rwLock.writeLock().lock();
        try { configuration = newValue; }
        finally { rwLock.writeLock().unlock(); }
    }

}
