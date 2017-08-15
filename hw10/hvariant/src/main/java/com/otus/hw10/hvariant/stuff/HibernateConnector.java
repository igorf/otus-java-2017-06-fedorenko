package com.otus.hw10.hvariant.stuff;

import com.otus.hw10.domain.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class HibernateConnector {
    private static SessionFactory sessionFactory;
    private static Configuration configuration;
    private static Logger logger = Logger.getLogger(HibernateConnector.class.getName());

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = createSessionFactory(configuration);
        }
        return sessionFactory;
    }

    private static Configuration createConfiguration(Properties properties) {
        Configuration configuration = new org.hibernate.cfg.Configuration();
        for (Map.Entry<?, ?> entry: properties.entrySet()) {
            configuration.setProperty((String) entry.getKey(), (String) entry.getValue());
        }

        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static Configuration fillEntities(Configuration configuration) {
        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(AdvancedUserDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        return configuration;
    }

    public static void connect(Properties properties) {
        configuration = createConfiguration(properties);
        configuration = fillEntities(configuration);
    }

    public static void disconnect() {
        try {
            sessionFactory.close();
            configuration = null;
            sessionFactory = null;
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }
    }
}
