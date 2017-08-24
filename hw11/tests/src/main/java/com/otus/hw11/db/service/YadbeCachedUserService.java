package com.otus.hw11.db.service;

import com.otus.hw11.domain.UserDataSet;
import com.otus.hw11.yace.CacheContainer;
import com.otus.hw11.yace.CacheEngine;
import com.otus.hw11.yace.YaceCacheEngine;

public class YadbeCachedUserService extends YadbeUserService {
    private static final int CACHE_SIZE = 10;
    private static final int CACHE_TTL = 10 * 60 * 1000;
    private final CacheEngine<Long, UserDataSet> cache = YaceCacheEngine.createSizedCache(CACHE_SIZE, CACHE_TTL);

    @Override
    public UserDataSet find(long id) {
        CacheContainer<Long, UserDataSet> cached = cache.get(id);
        if (cached != null) {
            return cached.getValue();
        }
        UserDataSet user = super.find(id);
        if (user != null) {
            cache.put(new CacheContainer<>(id, user));
        }

        return user;
    }

    @Override
    public UserDataSet save(UserDataSet user) {
        UserDataSet saved = super.save(user);
        if (saved != null && saved.getId() > 0) {
            cache.put(new CacheContainer<>(saved.getId(), saved));
        }
        return saved;
    }

    @Override
    public void remove(UserDataSet user) {
        if (user != null && user.getId() > 0) {
            cache.remove(user.getId());
        }
        super.remove(user);
    }
}
