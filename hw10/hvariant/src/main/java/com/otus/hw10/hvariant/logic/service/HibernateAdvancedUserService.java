package com.otus.hw10.hvariant.logic.service;

import com.otus.hw10.domain.AdvancedUserDataSet;
import com.otus.hw10.hvariant.logic.dao.HibernateAdvancedUserDao;
import com.otus.hw10.service.AdvancedUserService;

public class HibernateAdvancedUserService implements AdvancedUserService {
    private HibernateAdvancedUserDao userDao = new HibernateAdvancedUserDao();

    @Override
    public AdvancedUserDataSet save(AdvancedUserDataSet user) {
        return userDao.save(user);
    }

    @Override
    public AdvancedUserDataSet find(long id) {
        return userDao.find(id);
    }

    @Override
    public void remove(AdvancedUserDataSet user) {
        userDao.delete(user);
    }
}
