package com.otus.hw15.web.service;

import com.otus.hw15.data.model.User;
import com.otus.hw15.data.specific.UserLoginService;
import com.otus.hw15.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class LoginService implements UserLoginService {

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
        setUserLogged(user);
        return user != null;
    }

    @Override
    public void setUserLogged(User user) {
        if (user != null) {
            session.setAttribute(LOGGED_USER_ATTRIBUTE, user);
        }
    }

    public void logoff() {
        session.removeAttribute(LOGGED_USER_ATTRIBUTE);
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }
}
