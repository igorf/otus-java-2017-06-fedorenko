package com.otus.hw16.frontend.data;

import lombok.Data;
import net.sf.ehcache.Cache;

@Data
public class CacheSummary {
    private String name;
    private long size;
    private long globalTTL;
    private long idleTTL;
    private long hits;
    private long misses;

    public static CacheSummary fromCache(Cache cache) {
        CacheSummary result = new CacheSummary();
        result.setName(cache.getName());
        result.setGlobalTTL(cache.getCacheConfiguration().getTimeToLiveSeconds());
        result.setIdleTTL(cache.getCacheConfiguration().getTimeToIdleSeconds());
        result.setSize(cache.getSize());
        result.setHits(cache.getStatistics().cacheHitCount());
        result.setMisses(cache.getStatistics().cacheMissCount());
        return result;
    }
}
