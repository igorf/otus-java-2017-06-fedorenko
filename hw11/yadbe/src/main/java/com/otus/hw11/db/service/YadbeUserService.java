package com.otus.hw11.db.service;

import com.otus.hw11.db.dao.UserYadbeDao;
import com.otus.hw11.domain.UserDataSet;
import com.otus.hw11.service.UserService;
import com.otus.hw11.yadbe.AbstractYadbeService;

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
