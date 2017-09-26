package com.otus.hw15.data.specific.messages;

import com.otus.hw15.data.common.Address;
import com.otus.hw15.data.common.Message;
import com.otus.hw15.data.common.MessageAgent;
import com.otus.hw15.data.common.MessageBroker;
import com.otus.hw15.data.model.User;
import com.otus.hw15.data.specific.UserFetcher;

import javax.websocket.Session;

public class FindUserMessage implements Message {
    private long userId;
    private MessageBroker broker;
    private Address callbackAddress;
    private Session callbackSession;

    public FindUserMessage(MessageBroker broker, Address callbackAddress, Session callbackSession, long userId) {
        this.broker = broker;
        this.callbackAddress = callbackAddress;
        this.callbackSession = callbackSession;
        this.userId = userId;
    }

    @Override
    public void run(MessageAgent agent) {
        User user = ((UserFetcher) agent).find(userId);
        broker.sendMessage(callbackAddress, new VisualizeUserMessage(callbackSession, user));
    }
}
