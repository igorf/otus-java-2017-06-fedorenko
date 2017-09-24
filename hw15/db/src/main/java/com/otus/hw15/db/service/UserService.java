package com.otus.hw15.db.service;

import com.otus.hw15.db.model.User;
import com.otus.hw15.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;

    public User find(long id) {
        /*
        CacheContainer<Long, User> cached = userCache.get(id);
        if (cached != null) {
            cacheChangedNotifier.notifyClients();
            return cached.getValue();
        }
        User user = super.find(id);
        if (user != null) {
            userCache.put(new CacheContainer<>(id, user));
        }

        cacheChangedNotifier.notifyClients();
        return user;
        */
        return userRepository.findOne(id);
    }

    public User findByLoginAndPassword(String username, String password) {
        User user = userRepository.findOneByName(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
