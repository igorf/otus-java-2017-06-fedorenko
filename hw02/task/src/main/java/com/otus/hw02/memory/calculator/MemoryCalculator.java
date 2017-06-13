package com.otus.hw02.memory.calculator;

public class MemoryCalculator {
    public long calculate(MemoryObjectCreator createObjectRunnable) {
        try {
            long heap1 = memoryUsage();

            createObjectRunnable.run();

            long heap2 = memoryUsage();
            System.gc();
            Thread.currentThread().yield();
            return (heap2 - heap1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    private static long memoryUsage() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }
}
