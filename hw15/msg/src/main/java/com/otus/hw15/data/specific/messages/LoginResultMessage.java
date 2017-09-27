package com.otus.hw15.data.specific.messages;

import com.otus.hw15.data.specific.UserLoginCarrier;
import com.otus.hw15.data.common.AbstractMessage;
import com.otus.hw15.data.common.MessageAgent;

import javax.websocket.Session;

public class LoginResultMessage extends AbstractMessage {
    private Session callbackSession;
    boolean result = false;

    public LoginResultMessage(Session callbackSession, boolean result) {
        this.callbackSession = callbackSession;
        this.result = result;
    }

    @Override
    public void run(MessageAgent agent) {
        ((UserLoginCarrier) agent).onLogin(callbackSession, result);
    }
}
