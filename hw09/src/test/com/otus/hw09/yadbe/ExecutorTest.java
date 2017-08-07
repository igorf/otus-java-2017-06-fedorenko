package com.otus.hw09.yadbe;

import com.otus.hw09.homework.UserDataSet;
import com.otus.hw09.yadbe.connection.ConnectionHelper;
import com.otus.hw09.yadbe.connection.driver.YadbeMysql;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.Properties;

public class ExecutorTest {
    private Executor executor;

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("db", "otushw");
        properties.setProperty("user", "otus");
        properties.setProperty("password", "otus");
        ConnectionHelper.connect(new YadbeMysql(), properties);
        executor = new Executor();
    }

    @After
    public void tearDown() throws Exception {
        ConnectionHelper.disconnect();
    }

    @Test
    public void complex() throws Exception {
        UserDataSet user = new UserDataSet();
        user.setAge(33);
        user.setName("Test user");
        user = executor.save(user);
        assertNotEquals(0, user.getId());

        long id = user.getId();
        user.setAge(27);
        user.setName("Another user");
        executor.save(user);

        UserDataSet anotherUser = executor.load(id, UserDataSet.class);
        assertEquals(27, anotherUser.getAge());
        assertEquals("Another user", anotherUser.getName());

        executor.remove(user);
    }
}