package com.otus.hw13.web.service;

import com.otus.hw.yace.CacheEngine;
import com.otus.hw13.db.model.UserDataSet;
import com.otus.hw13.wrappers.CacheInfoWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CacheInfoService {

    @Autowired private CacheEngine<Long, UserDataSet> userCache;

    public Map<String, CacheEngine> findCaches() {
        Map<String, CacheEngine> result = new HashMap<>();
        result.put("USER_CACHE", userCache);

        return result;
    }

    public void cleanCache(String name) {
        CacheEngine cache = findCaches().get(name);
        if (cache != null) {
            cache.clean();
        }
    }

    public List<CacheInfoWrapper> cacheInfo() {
        Map<String, CacheEngine> caches = findCaches();
        List<CacheInfoWrapper> result = new ArrayList<>();
        for (String key: caches.keySet()) {
            CacheInfoWrapper w = new CacheInfoWrapper();
            w.setName(key);
            w.setGlobalTTL(caches.get(key).getElementTTL());
            w.setIdleTTL(caches.get(key).getIdleTTL());
            w.setMaxSize(caches.get(key).getMaxSize());
            w.setSize(caches.get(key).size());
            w.setHits(caches.get(key).hits());
            w.setMisses(caches.get(key).misses());

            result.add(w);
        }

        return result;
    }
}
