package com.otus.hw13.db.service.impl;

import com.otus.hw.yace.CacheContainer;
import com.otus.hw.yace.CacheEngine;
import com.otus.hw13.db.model.UserDataSet;
import com.otus.hw13.web.ws.CacheChangedNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YadbeCachedUserService extends YadbeUserService {

    @Autowired private CacheEngine<Long, UserDataSet> userCache;
    @Autowired private CacheChangedNotifier cacheChangedNotifier;

    @Override
    public UserDataSet find(long id) {
        CacheContainer<Long, UserDataSet> cached = userCache.get(id);
        if (cached != null) {
            cacheChangedNotifier.notifyClients();
            return cached.getValue();
        }
        UserDataSet user = super.find(id);
        if (user != null) {
            userCache.put(new CacheContainer<>(id, user));
        }

        cacheChangedNotifier.notifyClients();
        return user;
    }

    @Override
    public UserDataSet save(UserDataSet user) {
        UserDataSet saved = super.save(user);
        if (saved != null && saved.getId() > 0) {
            userCache.put(new CacheContainer<>(saved.getId(), saved));
        }

        cacheChangedNotifier.notifyClients();
        return saved;
    }

    @Override
    public void remove(UserDataSet user) {
        if (user != null && user.getId() > 0) {
            userCache.remove(user.getId());
        }

        cacheChangedNotifier.notifyClients();
        super.remove(user);
    }
}
