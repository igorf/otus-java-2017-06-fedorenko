package com.otus.hw12.managers;

import com.otus.hw.yace.CacheEngine;
import com.otus.hw.yace.YaceCacheEngine;
import com.otus.hw12.db.model.UserDataSet;

public class CacheManager {
    private static CacheEngine<Long, UserDataSet> userCache;

    public static CacheEngine<Long, UserDataSet> getUserCache() {
        if (userCache == null) {
            userCache = YaceCacheEngine.createSizedCache(10, 1000 * 60);
        }
        return userCache;
    }
}
