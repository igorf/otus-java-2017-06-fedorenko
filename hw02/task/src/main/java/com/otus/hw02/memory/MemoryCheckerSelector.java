package com.otus.hw02.memory;

public class MemoryCheckerSelector {
    public static MemoryChecker getMemoryChecker(MemoryCheckerType type) {
        switch (type) {
            case STREAM:
                return new StreamMemoryChcker();
            case HEAP:
                return new HeapMemoryChcker();
            case INSTRUMENTATION:
                return new InstrumentationMemoryChcker();
        }

        return getFallbackMemoryChecker();
    }

    public static MemoryChecker getDefault() {
        return getFallbackMemoryChecker();
    }

    private static MemoryChecker getFallbackMemoryChecker() {
        return new InstrumentationMemoryChcker();
    }
}
