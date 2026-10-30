package com.example.useraggregation.coordination;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TareaConCicleBarrier implements Runnable {

    private CyclicBarrier barrier;

    public TareaConCicleBarrier(CyclicBarrier barrier) {
        this.barrier = barrier;
    }


    @Override
    public void run() {
        try {
            System.out.println("Ejecutando una tarea ....");
            barrier.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}
