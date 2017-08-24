package com.otus.hw11;

import com.otus.hw11.db.service.YadbeCachedUserService;
import com.otus.hw11.service.UserService;
import com.otus.hw11.yadbe.connection.ConnectionHelper;
import com.otus.hw11.yadbe.connection.driver.YadbeMysql;
import org.junit.Test;

import java.util.Properties;

public class YadbeCachedServiceImplementationTest extends UserServiceTest {

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
        return new YadbeCachedUserService();
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