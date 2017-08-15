package com.otus.hw10.service;

import com.otus.hw10.domain.AdvancedUserDataSet;

public interface AdvancedUserService {
    AdvancedUserDataSet save(AdvancedUserDataSet user);
    AdvancedUserDataSet find(long id);
    void remove(AdvancedUserDataSet user);
}
