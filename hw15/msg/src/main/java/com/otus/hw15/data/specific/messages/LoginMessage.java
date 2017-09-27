package com.otus.hw15.data.specific.messages;

import com.otus.hw15.data.common.AbstractMessage;
import com.otus.hw15.data.common.Address;
import com.otus.hw15.data.common.MessageAgent;
import com.otus.hw15.data.model.User;
import com.otus.hw15.data.specific.UserFetcher;
import com.otus.hw15.data.specific.UserLoginService;

import javax.websocket.Session;

public class LoginMessage extends AbstractMessage {
    private String login;
    private String password;
    private Address callbackAddress;
    private Session callbackSession;
    private UserLoginService loginService;

    public LoginMessage(Address callbackAddress, Session callbackSession, UserLoginService loginService, String login, String password) {
        this.callbackAddress = callbackAddress;
        this.callbackSession = callbackSession;
        this.loginService = loginService;
        this.login = login;
        this.password = password;
    }

    @Override
    public void run(MessageAgent agent) {
        User user = ((UserFetcher) agent).findByLoginAndPassword(login, password);
        boolean result = user != null;
        if (user != null) {
            loginService.setUserLogged(user);
        }
        getMessageBroker().sendMessage(callbackAddress, new LoginResultMessage(callbackSession, result));
    }
}
