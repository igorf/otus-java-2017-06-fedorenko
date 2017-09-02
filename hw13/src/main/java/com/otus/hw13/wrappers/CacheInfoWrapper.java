package com.otus.hw13.wrappers;

import lombok.Data;

@Data
public class CacheInfoWrapper {
    private String name;
    private long maxSize;
    private long size;
    private long globalTTL;
    private long idleTTL;
    private long hits;
    private long misses;
}
