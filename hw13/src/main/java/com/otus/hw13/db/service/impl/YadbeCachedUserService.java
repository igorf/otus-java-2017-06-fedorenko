package com.otus.hw13.db.service.impl;

import com.otus.hw.yace.CacheContainer;
import com.otus.hw.yace.CacheEngine;
import com.otus.hw13.db.model.UserDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YadbeCachedUserService extends YadbeUserService {

    @Autowired private CacheEngine<Long, UserDataSet> userCache;

    @Override
    public UserDataSet find(long id) {
        CacheContainer<Long, UserDataSet> cached = userCache.get(id);
        if (cached != null) {
            return cached.getValue();
        }
        UserDataSet user = super.find(id);
        if (user != null) {
            userCache.put(new CacheContainer<>(id, user));
        }

        return user;
    }

    @Override
    public UserDataSet save(UserDataSet user) {
        UserDataSet saved = super.save(user);
        if (saved != null && saved.getId() > 0) {
            userCache.put(new CacheContainer<>(saved.getId(), saved));
        }
        return saved;
    }

    @Override
    public void remove(UserDataSet user) {
        if (user != null && user.getId() > 0) {
            userCache.remove(user.getId());
        }
        super.remove(user);
    }
}
