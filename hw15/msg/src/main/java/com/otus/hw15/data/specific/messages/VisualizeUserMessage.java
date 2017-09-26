package com.otus.hw15.data.specific.messages;

import com.otus.hw15.data.common.Message;
import com.otus.hw15.data.common.MessageAgent;
import com.otus.hw15.data.model.User;
import com.otus.hw15.data.specific.UserSessionVisualizer;

import javax.websocket.Session;

public class VisualizeUserMessage implements Message {
    private Session session;
    private User user;

    VisualizeUserMessage(Session session, User user) {
        this.session = session;
        this.user = user;
    }

    @Override
    public void run(MessageAgent agent) {
        ((UserSessionVisualizer) agent).showUser(session, user);
    }
}
