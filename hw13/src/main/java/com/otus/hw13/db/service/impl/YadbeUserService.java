package com.otus.hw13.db.service.impl;

import com.otus.hw.yadbe.AbstractYadbeService;
import com.otus.hw13.db.dao.UserYadbeDao;
import com.otus.hw13.db.model.UserDataSet;
import com.otus.hw13.db.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class YadbeUserService extends AbstractYadbeService implements UserService {

    private UserYadbeDao dao = new UserYadbeDao();

    public YadbeUserService() {
        super();
        dao.setExecutor(executor);
    }

    @Override
    public UserDataSet save(UserDataSet user) {
        return dao.save(user);
    }

    @Override
    public UserDataSet find(long id) {
        return dao.findById(id);
    }

    @Override
    public UserDataSet findByLoginAndPassword(String name, String password) {
        return dao.findUserByLoginAndPassword(name, password);
    }

    @Override
    public void remove(UserDataSet user) {
        dao.remove(user);
    }
}
