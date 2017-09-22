package com.otus.hw14.sorter;

import com.otus.hw14.utils.ArrayMerger;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class Sorter {
    private final static int SPLIT_THRESHOLD = 2;
    private final static int DEFAULT_THREADS_COUNT = 4;

    public void sort(int[] array) throws Exception {
        if ((array.length / DEFAULT_THREADS_COUNT) < SPLIT_THRESHOLD) {
            Arrays.sort(array);
        } else {
            multithreadSort(array, DEFAULT_THREADS_COUNT);
        }
    }

    private void multithreadSort(int[] array, int threads) throws Exception {
        CountDownLatch latch = new CountDownLatch(threads);
        int increment = array.length / threads;
        SortTask[] tasks = new SortTask[threads];

        if (increment < SPLIT_THRESHOLD) {
            throw new IllegalAccessException();
        }

        for (int i = 0; i < threads; i++) {
            int start = i * increment;
            int end = Math.min(start + increment, array.length);
            int len = end - start;
            int chunk[] = new int[len];
            System.arraycopy(array, start, chunk, 0, len);

            tasks[i] = new SortTask(latch, chunk);
            tasks[i].start();
        }

        latch.await();
        int result[] = tasks[0].getArray();
        for (int i = 1; i < tasks.length; i++) {
            result = ArrayMerger.merge(result, tasks[i].getArray());
        }
        System.arraycopy(result, 0, array, 0, result.length);
    }
}
