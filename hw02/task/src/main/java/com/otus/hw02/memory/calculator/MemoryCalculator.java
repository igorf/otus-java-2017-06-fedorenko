package com.otus.hw02.memory.calculator;

public class MemoryCalculator {
    public long calculate(MemoryObjectCreator createObjectRunnable) {
        try {
            System.gc();
            Thread.sleep(10);
            int objectsCount = 1000000;
            Object[] storage = new Object[objectsCount];
            long heap1 = memoryUsage();

            for (int i = 0; i < objectsCount; i++) {
                storage[i] = createObjectRunnable.run();
            }

            long heap2 = memoryUsage();
            return Math.round ((float)(heap2 - heap1) / objectsCount);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    private static long memoryUsage() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }
}
