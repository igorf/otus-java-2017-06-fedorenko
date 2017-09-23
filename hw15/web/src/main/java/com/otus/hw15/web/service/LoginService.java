package com.otus.hw15.web.service;

import com.otus.hw15.db.model.User;
import com.otus.hw15.db.service.UserService;
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

    public User getUser() {
        return (User) session.getAttribute(LOGGED_USER_ATTRIBUTE);
    }

    public boolean login(String username, String password) {
        User user = userService.findByLoginAndPassword(username, password);
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
