package com.otus.hw14.sorter;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class Sorter {
    private final static int SPLIT_THRESHOLD = 2;
    private final static int DEFAULT_THREADS_COUNT = 4;

    public void sort(Object[] array) throws Exception {
        multithreadSort(array, DEFAULT_THREADS_COUNT);
    }

    public void multithreadSort(Object[] array, int threads) throws Exception {
        CountDownLatch latch = new CountDownLatch(threads);
        int increment = array.length / threads;

        if (increment < SPLIT_THRESHOLD) {
            throw new IllegalAccessException();
        }

        for (int i = 0; i < threads; i++) {
            int start = i * increment;
            int end = Math.min(start + increment, array.length);

            new SortTask(latch, start, end, array).start();
        }

        latch.await();
        Arrays.sort(array, 0, array.length);
    }
}
