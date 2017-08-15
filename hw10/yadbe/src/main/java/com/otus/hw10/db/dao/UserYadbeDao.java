package com.otus.hw10.db.dao;

import com.otus.hw10.domain.UserDataSet;
import com.otus.hw10.yadbe.AbstractYadbeDao;

public class UserYadbeDao extends AbstractYadbeDao {

    public UserDataSet findById(long id) {
        try {
            return executor.load(id, UserDataSet.class);
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }
        return null;
    }

    public UserDataSet save(UserDataSet user) {
        try {
             return executor.save(user);
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }
        return null;
    }

    public void remove(UserDataSet user) {
        try {
            executor.remove(user);
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }
    }

    @Override
    protected String getLoggerID() {
        return "UserDAO";
    }
}
