package com.otus.hw15.web.service;

import com.otus.hw15.wrappers.CacheInfoWrapper;
import net.sf.ehcache.Cache;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CacheInfoService {

    public Map<String, Cache> findCaches() {
        return new HashMap<>();
    }

    public void cleanCache(String name) {
    }

    public List<CacheInfoWrapper> cacheInfo() {
        List<CacheInfoWrapper> result = new ArrayList<>();
        return result;
    }
}
