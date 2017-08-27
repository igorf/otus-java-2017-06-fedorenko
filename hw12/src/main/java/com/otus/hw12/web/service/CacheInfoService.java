package com.otus.hw12.web.service;

import com.otus.hw.yace.CacheEngine;
import com.otus.hw12.managers.CacheManager;

import java.util.HashMap;
import java.util.Map;

public class CacheInfoService {
    public Map<String, CacheEngine> findCaches() {
        Map<String, CacheEngine> result = new HashMap<>();
        result.put("USER_CACHE", CacheManager.getUserCache());

        return result;
    }

    public void cleanCache(String name) {
        CacheEngine cache = findCaches().get(name);
        if (cache != null) {
            cache.clean();
        }
    }
}
