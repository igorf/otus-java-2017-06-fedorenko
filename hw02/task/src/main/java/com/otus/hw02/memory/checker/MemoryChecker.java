package com.otus.hw02.memory.checker;

public interface MemoryChecker {
    long getObjectSize(Object object) throws Exception;
}
