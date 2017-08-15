package com.otus.hw10.hvariant.stuff;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.function.Function;

public class SessionHelper {

    public static <R> R runInSession(Function<Session, R> function) {
        SessionFactory sessionFactory = HibernateConnector.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }
}
