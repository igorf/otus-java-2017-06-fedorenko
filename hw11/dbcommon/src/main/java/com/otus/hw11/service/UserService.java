package com.otus.hw11.service;

import com.otus.hw11.domain.UserDataSet;

public interface UserService {
    UserDataSet save(UserDataSet user);
    UserDataSet find(long id);
    void remove(UserDataSet user);

    // И так далее
}
