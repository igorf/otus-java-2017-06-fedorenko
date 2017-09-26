package com.otus.hw15.db.service;

import com.otus.hw15.db.model.User;
import com.otus.hw15.db.repository.UserRepository;
import com.otus.hw15.msg.common.Address;
import com.otus.hw15.msg.common.MessageBroker;
import com.otus.hw15.msg.specific.messages.CacheChangedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private MessageBroker messageBroker;
    @Autowired private Address screenUpdaterAddress;

    @Cacheable(value="users", key="#id")
    public User find(long id) {
        User user = userRepository.findOne(id);
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
