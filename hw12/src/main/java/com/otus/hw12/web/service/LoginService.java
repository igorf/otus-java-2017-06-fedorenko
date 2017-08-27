package com.otus.hw12.web.service;

import com.otus.hw12.db.model.UserDataSet;
import com.otus.hw12.managers.DbServiceManager;

import javax.servlet.http.HttpServletRequest;

public class LoginService {

    private final static String LOGGED_USER_ATTRIBUTE = "LOGGED_USER";
    private HttpServletRequest request;

    public LoginService(HttpServletRequest request) {
        this.request = request;
    }

    public boolean isLogged() {
        return getUser() != null;
    }

    public UserDataSet getUser() {
        return (UserDataSet) request.getSession().getAttribute(LOGGED_USER_ATTRIBUTE);
    }

    public boolean login(String username, String password) {
        UserDataSet user = DbServiceManager.getUserService().findByLoginAndPassword(username, password);
        if (user != null) {
            request.getSession().setAttribute(LOGGED_USER_ATTRIBUTE, user);
        }
        return user != null;
    }

    public void logoff() {
        request.getSession().removeAttribute(LOGGED_USER_ATTRIBUTE);
    }
}
