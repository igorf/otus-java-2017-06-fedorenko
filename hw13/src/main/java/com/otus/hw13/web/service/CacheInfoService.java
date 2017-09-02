package com.otus.hw13.web.service;

import com.otus.hw.yace.CacheEngine;
import com.otus.hw13.db.model.UserDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
}
