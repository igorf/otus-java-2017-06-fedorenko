package com.otus.hw15.data.specific.messages;

import com.otus.hw15.data.common.AbstractMessage;
import com.otus.hw15.data.common.Address;
import com.otus.hw15.data.common.MessageAgent;
import com.otus.hw15.data.model.User;
import com.otus.hw15.data.specific.UserFetcher;

import javax.websocket.Session;

public class FindUserMessage extends AbstractMessage {
    private long userId;
    private Address callbackAddress;
    private Session callbackSession;

    public FindUserMessage(Address callbackAddress, Session callbackSession, long userId) {
        this.callbackAddress = callbackAddress;
        this.callbackSession = callbackSession;
        this.userId = userId;
    }

    @Override
    public void run(MessageAgent agent) {
        User user = ((UserFetcher) agent).find(userId);
        getMessageBroker().sendMessage(callbackAddress, new VisualizeUserMessage(callbackSession, user));
    }
}
