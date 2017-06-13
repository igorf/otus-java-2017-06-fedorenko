package com.otus.hw02.memory.checker;

public class MemoryCheckerSelector {
    public static MemoryChecker getMemoryChecker(MemoryCheckerType type) {
        switch (type) {
            case INSTRUMENTATION:
                return new InstrumentationMemoryChecker();
            case JAMM:
                return new JammMemoryChecker();
        }

        return getFallbackMemoryChecker();
    }

    public static MemoryChecker getDefault() {
        return getFallbackMemoryChecker();
    }

    private static MemoryChecker getFallbackMemoryChecker() {
        return new JammMemoryChecker();
    }
}
