package com.otus.hw15.db.service;

import com.otus.hw15.data.common.Address;
import com.otus.hw15.data.common.MessageAgent;
import com.otus.hw15.data.common.MessageBroker;
import com.otus.hw15.data.model.User;
import com.otus.hw15.data.specific.UserFetcher;
import com.otus.hw15.data.specific.messages.CacheChangedMessage;
import com.otus.hw15.db.repository.UserRepository;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class UserService implements MessageAgent, UserFetcher {

    @Autowired private UserRepository userRepository;
    @Autowired private EhCacheCacheManager cacheManager;
    @Autowired private ApplicationContext context;

    private final static String CACHE_KEY = "users"; //in real project use @Cacheable instead

    @Override
    public User find(long id) {
        User user = null;
        Cache cache = cacheManager.getCacheManager().getCache(CACHE_KEY);
        boolean found = false;
        if (cache != null) {
            Element e = cache.get(id);
            if (e != null) {
                user = (User) e.getObjectValue();
                found = true;
            }
        }
        if (!found) {
            user = userRepository.findOne(id);
            if (cache != null && user != null) {
                cache.put(new Element(id, user));
            }
        }

        Address screenUpdaterAddress = context.getBean("screenUpdaterAddress", Address.class);
        MessageBroker messageBroker = context.getBean("messageBroker", MessageBroker.class);
        messageBroker.sendMessage(screenUpdaterAddress, new CacheChangedMessage());
        return user;
    }

    @Override
    public User findByLoginAndPassword(String username, String password) {
        User user = userRepository.findOneByName(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public Address getAddress() {
        return context.getBean("userServiceAddress", Address.class);
    }
}
