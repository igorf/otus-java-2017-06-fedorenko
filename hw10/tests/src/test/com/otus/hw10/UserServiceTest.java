package com.otus.hw10;

import com.otus.hw10.domain.UserDataSet;
import com.otus.hw10.service.UserService;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public abstract class UserServiceTest {

    private UserService userService;

    abstract void prepare();
    abstract UserService createUserService();
    abstract void shutdown();

    @Before
    public void setUp() throws Exception {
        prepare();
        userService = createUserService();
    }

    @After
    public void tearDown() throws Exception {
        shutdown();
    }

    void complexTest() throws Exception {
        UserDataSet user = new UserDataSet();
        user.setAge(33);
        user.setName("Test user");
        user = userService.save(user);
        assertNotEquals(0, user.getId());

        long id = user.getId();
        user.setAge(27);
        user.setName("Another user");
        userService.save(user);

        UserDataSet anotherUser = userService.find(id);
        assertEquals(27, anotherUser.getAge());
        assertEquals("Another user", anotherUser.getName());

        userService.remove(user);
    }
}