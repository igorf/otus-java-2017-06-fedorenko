package com.otus.hw02.memory.checker;

import org.github.jamm.MemoryMeter;

public class JammMemoryChecker implements MemoryChecker {

    @Override
    public long getObjectSize(Object object) throws Exception {
        MemoryMeter meter = new MemoryMeter();
        return meter.measureDeep(object);
    }
}
