package com.otus.hw15.db.service;

import com.otus.hw15.db.model.User;
import com.otus.hw15.db.repository.UserRepository;
import com.otus.hw15.msg.common.Address;
import com.otus.hw15.msg.common.MessageBroker;
import com.otus.hw15.msg.specific.messages.CacheChangedMessage;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private MessageBroker messageBroker;
    @Autowired private Address screenUpdaterAddress;
    @Autowired private EhCacheCacheManager cacheManager;

    private final static String CACHE_KEY = "users"; //in real project use @Cacheable instead

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
        messageBroker.sendMessage(screenUpdaterAddress, new CacheChangedMessage());
        return user;
    }

    public User findByLoginAndPassword(String username, String password) {
        User user = userRepository.findOneByName(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
