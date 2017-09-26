package com.otus.hw15.data.specific.messages;

import com.otus.hw15.data.common.Message;
import com.otus.hw15.data.common.MessageAgent;
import com.otus.hw15.data.specific.ClientsNotifier;

public class CacheChangedMessage implements Message {
    @Override
    public void run(MessageAgent agent) {
        ((ClientsNotifier) agent).notifyClients();
    }
}
