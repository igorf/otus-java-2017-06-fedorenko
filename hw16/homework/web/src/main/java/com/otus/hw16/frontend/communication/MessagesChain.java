package com.otus.hw16.frontend.communication;

import com.otus.hw16.frontend.web.ws.CacheChangedNotifier;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MessagesChain {
    private static final String CACHE_NAME = "messages";
    @Autowired private CacheManager cacheManager;
    @Autowired private CacheChangedNotifier cacheChangedNotifier;

    public MessageAction popAction(UUID address) {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        Element element = cache.get(address);
        if (element != null) {
            MessageAction client = (MessageAction) element.getObjectValue();
            cache.remove(address);
            cacheChangedNotifier.notifyClients();
            return client;
        }
        cacheChangedNotifier.notifyClients();
        return null;
    }

    public void pushAction(UUID address, MessageAction action) {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        cache.put(new Element(address, action));
        cacheChangedNotifier.notifyClients();
    }
}
