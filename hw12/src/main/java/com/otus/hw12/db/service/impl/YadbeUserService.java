package com.otus.hw12.db.service.impl;

import com.otus.hw.yadbe.AbstractYadbeService;
import com.otus.hw12.db.dao.UserYadbeDao;
import com.otus.hw12.db.model.UserDataSet;
import com.otus.hw12.db.service.UserService;

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
    public UserDataSet findByLoginAndPassword(String name, String password) {
        return userDao.findUserByLoginAndPassword(name, password);
    }

    @Override
    public void remove(UserDataSet user) {
        userDao.remove(user);
    }
}
