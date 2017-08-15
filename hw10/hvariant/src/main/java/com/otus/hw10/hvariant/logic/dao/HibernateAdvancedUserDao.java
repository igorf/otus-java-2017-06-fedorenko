package com.otus.hw10.hvariant.logic.dao;

import com.otus.hw10.domain.AdvancedUserDataSet;
import com.otus.hw10.hvariant.stuff.SessionHelper;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class HibernateAdvancedUserDao {
    public AdvancedUserDataSet find(long id) {
        return SessionHelper.runInSession(session -> {
            Criteria criteria = session.createCriteria(AdvancedUserDataSet.class);
            criteria.add(Restrictions.eq("id", id));
            List<AdvancedUserDataSet> found = criteria.list();
            if (found.size() > 0) {
                return found.get(0);
            }
            return null;
        });
    }

    public AdvancedUserDataSet save(AdvancedUserDataSet user) {
        if (user.getId() > 0) {
            return SessionHelper.runInSession(session -> {session.update(user); return user;});
        } else {
            long id = SessionHelper.runInSession(session -> (Long) session.save(user));
            user.setId(id);
            return user;
        }
    }

    public void delete(AdvancedUserDataSet user) {
        SessionHelper.runInSession(session -> {session.delete(user); return null;});
    }
}
