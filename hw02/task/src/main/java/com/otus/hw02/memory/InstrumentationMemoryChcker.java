package com.otus.hw02.memory;

import com.otus.hw02.agent.ObjectSizeFetcher;

public class InstrumentationMemoryChcker implements MemoryChecker {
    @Override
    public long getObjectSize(Object object) throws Exception {
        return ObjectSizeFetcher.getObjectSize(object);
    }
}
