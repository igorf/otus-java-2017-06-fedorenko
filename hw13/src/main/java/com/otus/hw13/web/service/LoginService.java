package com.otus.hw13.web.service;

import com.otus.hw13.db.model.UserDataSet;
import com.otus.hw13.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class LoginService {

    private final static String LOGGED_USER_ATTRIBUTE = "LOGGED_USER";
    private HttpSession session;
    @Autowired private UserService userService;

    public boolean isLogged() {
        return getUser() != null;
    }

    public UserDataSet getUser() {
        return (UserDataSet) session.getAttribute(LOGGED_USER_ATTRIBUTE);
    }

    public boolean login(String username, String password) {
        UserDataSet user = userService.findByLoginAndPassword(username, password);
        if (user != null) {
            session.setAttribute(LOGGED_USER_ATTRIBUTE, user);
        }
        return user != null;
    }

    public void logoff() {
        session.removeAttribute(LOGGED_USER_ATTRIBUTE);
    }


    public void setSession(HttpSession session) {
        this.session = session;
    }
}
