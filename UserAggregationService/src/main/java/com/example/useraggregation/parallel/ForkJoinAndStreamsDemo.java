package com.example.useraggregation.parallel;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public final class ForkJoinAndStreamsDemo {
    private static final class SumTask extends RecursiveTask<Long> {
        private final int start;
        private final int end;
        private static final int THRESHOLD = 1_000;

        SumTask(int start, int end) { this.start = start; this.end = end; }

        @Override
        protected Long compute() {
            if (end - start <= THRESHOLD) {
                long sum = 0;
                for (int i = start; i < end; i++) sum += i;
                return sum;
            }
            int middle = start + (end - start) / 2;
            SumTask left = new SumTask(start, middle);
            SumTask right = new SumTask(middle, end);
            left.fork(); // ejecuta una parte en paralelo
            long rightResult = right.compute();
            return left.join() + rightResult;
        }
    }

    public static void run() {
        try (ForkJoinPool pool = new ForkJoinPool()) {
            System.out.println("Suma ForkJoin: " + pool.invoke(new SumTask(0, 100_000)));
        }
        List<Integer> values = java.util.stream.IntStream.range(0, 100_000).boxed().toList();
        long evenCount = values.parallelStream().filter(n -> n % 2 == 0).count();
        System.out.println("Pares: " + evenCount);
        // No introduzcas efectos laterales compartidos dentro de parallelStream().
    }
}
