package com.otus.hw15.web.service;

import com.otus.hw15.data.CacheSummary;
import net.sf.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CacheService {

    @Autowired private EhCacheCacheManager cacheManager;

    public Map<String, Cache> findCaches() {
        Map<String, Cache> caches = new HashMap<>();
        for (String cacheName: cacheManager.getCacheManager().getCacheNames()) {
            caches.put(cacheName, cacheManager.getCacheManager().getCache(cacheName));
        }
        return caches;
    }

    public void cleanCache(String name) {
        Cache cache = cacheManager.getCacheManager().getCache(name);
        if (cache != null) {
            cache.removeAll();
        }
    }

    public List<CacheSummary> summary() {
        List<CacheSummary> result = new ArrayList<>();
        for (Cache cache: findCaches().values()) {
            result.add(CacheSummary.fromCache(cache));
        }
        return result;
    }
}
