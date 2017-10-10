package com.otus.hw16.serverapp.common;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.lib.app.MsgClient;

import java.util.UUID;

@Component
public class MessagesCache {
    private static final String CACHE_NAME = "messages";
    @Autowired
    private CacheManager cacheManager;

    public MsgClient popCallbackSender(UUID address) {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        Element element = cache.get(address);
        if (element != null) {
            MsgClient client = (MsgClient) element.getObjectValue();
            cache.remove(address);
            return client;
        }
        return null;
    }

    public void pushSender(UUID address, MsgClient sender) {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        cache.put(new Element(address, sender));
    }
}
