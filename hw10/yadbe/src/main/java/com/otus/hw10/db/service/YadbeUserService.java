package com.otus.hw10.db.service;

import com.otus.hw10.db.dao.UserYadbeDao;
import com.otus.hw10.domain.UserDataSet;
import com.otus.hw10.service.UserService;
import com.otus.hw10.yadbe.AbstractYadbeService;

public class YadbeUserService extends AbstractYadbeService implements UserService {
    private UserYadbeDao userDao = new UserYadbeDao();

    public YadbeUserService() {
        super();
        userDao.setExecutor(executor);
    }

    @Override
    public UserDataSet save(UserDataSet user) {
        return userDao.save(user);
    }

    @Override
    public UserDataSet find(long id) {
        return userDao.findById(id);
    }

    @Override
    public void remove(UserDataSet user) {
        userDao.remove(user);
    }
}
