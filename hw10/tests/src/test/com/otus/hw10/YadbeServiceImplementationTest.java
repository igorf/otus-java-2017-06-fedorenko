package com.otus.hw10;

import com.otus.hw10.db.service.YadbeUserService;
import com.otus.hw10.service.UserService;
import com.otus.hw10.yadbe.connection.ConnectionHelper;
import com.otus.hw10.yadbe.connection.driver.YadbeMysql;
import org.junit.Test;

import java.util.Properties;

public class YadbeServiceImplementationTest extends UserServiceTest {

    @Override
    void prepare() {
        Properties properties = new Properties();
        properties.setProperty("db", "otushw");
        properties.setProperty("user", "otus");
        properties.setProperty("password", "otus");

        ConnectionHelper.connect(new YadbeMysql(), properties);
    }

    @Override
    UserService createUserService() {
        return new YadbeUserService();
    }

    @Override
    void shutdown() {
        ConnectionHelper.disconnect();
    }

    @Test
    public void complex() throws Exception {
        System.out.println("Complex test for YADBE started");
        this.complexTest();
        System.out.println("Complex test for YADBE completed");
    }
}