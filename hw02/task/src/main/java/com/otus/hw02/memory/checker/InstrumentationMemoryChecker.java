package com.otus.hw02.memory.checker;

import com.otus.hw02.agent.ObjectSizeFetcher;

public class InstrumentationMemoryChecker implements MemoryChecker {
    @Override
    public long getObjectSize(Object object) throws Exception {
        return ObjectSizeFetcher.getObjectSize(object);
    }
}
