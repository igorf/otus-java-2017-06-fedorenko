package com.otus.hw15.msg.specific.messages;

import com.otus.hw15.msg.common.Message;
import com.otus.hw15.msg.common.MessageAgent;
import com.otus.hw15.msg.specific.ClientsNotifier;

public class CacheChangedMessage implements Message {
    @Override
    public void run(MessageAgent agent) {
        ((ClientsNotifier) agent).notifyClients();
    }
}
