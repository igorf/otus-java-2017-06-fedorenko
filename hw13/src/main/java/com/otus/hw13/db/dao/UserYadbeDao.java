package com.otus.hw13.db.dao;

import com.otus.hw.yadbe.AbstractYadbeDao;
import com.otus.hw13.db.model.UserDataSet;

import java.util.List;

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

    public UserDataSet findUserByLoginAndPassword(String login, String password) {
        try {
            List<UserDataSet> found = executor.findWhere("name='" + login + "' and password = '" + password + "'", UserDataSet.class);
            if (found.size() > 0) {
                return found.get(0);
            }
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }
        return null;
    }

    @Override
    protected String getLoggerID() {
        return "UserDAO";
    }
}
