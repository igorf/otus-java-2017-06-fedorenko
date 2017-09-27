package com.otus.hw15.web.service;

import com.otus.hw15.data.model.User;
import com.otus.hw15.data.specific.UserLoginService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class LoginService implements UserLoginService {

    private final static String LOGGED_USER_ATTRIBUTE = "LOGGED_USER";
    private HttpSession session;

    public boolean isLogged() {
        return getUser() != null;
    }

    public User getUser() {
        return (User) session.getAttribute(LOGGED_USER_ATTRIBUTE);
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
