package com.otus.hw15.data.specific.messages;

import com.otus.hw15.data.common.AbstractMessage;
import com.otus.hw15.data.common.MessageAgent;
import com.otus.hw15.data.specific.ClientsNotifier;

public class CacheChangedMessage extends AbstractMessage {
    @Override
    public void run(MessageAgent agent) {
        ((ClientsNotifier) agent).notifyClients();
    }
}
