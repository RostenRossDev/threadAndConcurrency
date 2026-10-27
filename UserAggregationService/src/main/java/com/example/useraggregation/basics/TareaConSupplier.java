package com.example.useraggregation.basics;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public class TareaConSupplier implements Supplier<Integer> {

    @Override
    public Integer get() {
        long start = System.nanoTime();

        for (int i = 0; i < 1000000; i++) {
            System.out.println(LocalDateTime.now());
        }

        long duration = System.nanoTime() - start;
        double millis = duration / 1_000_000.0;    // 1500.0 ms
        double seg = duration / 1_000_000_000.0;    // 1500.0 ms
        IO.println(String.format("Se ejecuto en %.2f mili", millis));
        IO.println(String.format("Se ejecuto en %.2f segundos", seg));
        return 100;
    }
}
