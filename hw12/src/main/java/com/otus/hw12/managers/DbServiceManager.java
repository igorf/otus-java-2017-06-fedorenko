package com.otus.hw12.managers;

import com.otus.hw12.db.service.UserService;
import com.otus.hw12.db.service.impl.YadbeCachedUserService;

public class DbServiceManager {
    private static UserService userService;

    public static UserService getUserService() {
        if (userService == null) {
            userService = new YadbeCachedUserService();
        }
        return userService;
    }
}
