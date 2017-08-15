package com.otus.hw10.hvariant.logic.dao;

import com.otus.hw10.domain.UserDataSet;
import com.otus.hw10.hvariant.stuff.SessionHelper;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class HibernateUserDao {
    public UserDataSet find(long id) {
        return SessionHelper.runInSession(session -> {
            Criteria criteria = session.createCriteria(UserDataSet.class);
            criteria.add(Restrictions.eq("id", id));
            List<UserDataSet> found = criteria.list();
            if (found.size() > 0) {
                return found.get(0);
            }
            return null;
        });
    }

    public UserDataSet save(UserDataSet user) {
        if (user.getId() <= 0) {
            long id = SessionHelper.runInSession(session -> (Long) session.save(user));
            user.setId(id);
            return user;
        } else {
            return SessionHelper.runInSession(session -> {session.update(user); return user;});
        }
    }

    public void delete(UserDataSet user) {
        SessionHelper.runInSession(session -> {session.delete(user); return null;});
    }
}
