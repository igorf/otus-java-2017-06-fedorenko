package com.otus.hw10;

import com.otus.hw10.hvariant.logic.service.HibernateUserService;
import com.otus.hw10.hvariant.stuff.HibernateConnector;
import com.otus.hw10.service.UserService;
import org.junit.Test;

public class HibernateServiceImplementationTest extends UserServiceTest {
    @Override
    void prepare() {
        HibernateConnector.connect(HibernatePropertiesCreator.getProperties());
    }

    @Override
    UserService createUserService() {
        return new HibernateUserService();
    }

    @Override
    void shutdown() {
        HibernateConnector.disconnect();
    }

    @Test
    public void complex() throws Exception {
        System.out.println("Hibernate complex test started");
        this.complexTest();
        System.out.println("Hibernate complex test completed");
    }
}