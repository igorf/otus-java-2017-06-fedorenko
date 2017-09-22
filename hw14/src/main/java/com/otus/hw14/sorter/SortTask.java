package com.otus.hw14.sorter;

import lombok.Getter;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

class SortTask extends Thread {
    private CountDownLatch latch;
    @Getter
    private int[] array;

    public SortTask(CountDownLatch latch, int[] array) {
        this.array = array;
        this.latch = latch;
    }

    public void run() {
        Arrays.sort(array);
        latch.countDown();
    }
}
