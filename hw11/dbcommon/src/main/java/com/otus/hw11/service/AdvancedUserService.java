package com.otus.hw11.service;

import com.otus.hw11.domain.AdvancedUserDataSet;

public interface AdvancedUserService {
    AdvancedUserDataSet save(AdvancedUserDataSet user);
    AdvancedUserDataSet find(long id);
    void remove(AdvancedUserDataSet user);
}
