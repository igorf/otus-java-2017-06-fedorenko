package com.otus.hw15.db.service;

import com.otus.hw15.db.model.User;
import com.otus.hw15.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;

    @Cacheable(value="users", key="#id")
    public User find(long id) {
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
