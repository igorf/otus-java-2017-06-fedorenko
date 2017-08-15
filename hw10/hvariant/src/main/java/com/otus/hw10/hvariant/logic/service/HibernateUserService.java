package com.otus.hw10.hvariant.logic.service;

import com.otus.hw10.domain.UserDataSet;
import com.otus.hw10.hvariant.logic.dao.HibernateUserDao;
import com.otus.hw10.service.UserService;

public class HibernateUserService implements UserService {
    private HibernateUserDao userDao = new HibernateUserDao();

    @Override
    public UserDataSet save(UserDataSet user) {
        return userDao.save(user);
    }

    @Override
    public UserDataSet find(long id) {
        return userDao.find(id);
    }

    @Override
    public void remove(UserDataSet user) {
        userDao.delete(user);
    }
}
