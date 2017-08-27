package com.otus.hw12.db.service.impl;

import com.otus.hw.yace.CacheContainer;
import com.otus.hw.yace.CacheEngine;
import com.otus.hw12.db.model.UserDataSet;
import com.otus.hw12.managers.CacheManager;

public class YadbeCachedUserService extends YadbeUserService {
    private final CacheEngine<Long, UserDataSet> cache = CacheManager.getUserCache();

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
