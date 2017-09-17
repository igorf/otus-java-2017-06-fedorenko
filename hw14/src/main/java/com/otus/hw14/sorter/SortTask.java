package com.otus.hw14.sorter;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

class SortTask extends Thread {
    private CountDownLatch latch;
    private int from;
    private int to;
    private Object[] array;

    public SortTask(CountDownLatch latch, int from, int to, Object[] array) {
        this.from = from;
        this.to = to;
        this.array = array;
        this.latch = latch;
    }

    public void run() {
        Arrays.sort(array, from, to);
        latch.countDown();
    }
}
