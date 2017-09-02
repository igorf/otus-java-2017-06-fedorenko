package com.otus.hw13.db.service;

import com.otus.hw13.db.model.UserDataSet;

public interface UserService {
    UserDataSet save(UserDataSet user);
    UserDataSet find(long id);
    UserDataSet findByLoginAndPassword(String name, String password);
    void remove(UserDataSet user);
}
