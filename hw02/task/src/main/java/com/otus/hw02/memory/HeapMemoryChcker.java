package com.otus.hw02.memory;

public class HeapMemoryChcker implements MemoryChecker {
    private static final Runtime runtime = Runtime.getRuntime();

    @Override
    public long getObjectSize(Object object) throws Exception {
        throw new Exception("Not implemented");
    }

    //Внаглую взято с http://www.javaworld.com/article/2077496/testing-debugging/java-tip-130--do-you-know-your-data-size-.html
    private void runGC () throws Exception {
        for (int r = 0; r < 4; ++ r) _runGC ();
    }

    private void _runGC () throws Exception {
        long usedMem1 = usedMemory(), usedMem2 = Long.MAX_VALUE;
        for (int i = 0; (usedMem1 < usedMem2) && (i < 500); ++ i) {
            runtime.runFinalization ();
            runtime.gc ();
            Thread.currentThread ().yield();

            usedMem2 = usedMem1;
            usedMem1 = usedMemory ();
        }
    }

    private long usedMemory () {
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
