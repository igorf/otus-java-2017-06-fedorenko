package com.otus.hw10.service;

import com.otus.hw10.domain.UserDataSet;

public interface UserService {
    UserDataSet save(UserDataSet user);
    UserDataSet find(long id);
    void remove(UserDataSet user);

    // И так далее
}
