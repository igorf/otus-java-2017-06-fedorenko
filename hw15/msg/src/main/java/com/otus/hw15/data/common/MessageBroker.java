package com.otus.hw15.data.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class MessageBroker {

    @Autowired
    private Set<MessageAgent> agentSet;
    private final Map<MessageAgent, ConcurrentLinkedQueue<AbstractMessage>> messages = new HashMap<>();
    private final Map<Address, MessageAgent> receivers = new HashMap<>();

    public void sendMessage(Address receiver, AbstractMessage message) {
        message.setMessageBroker(this);
        ConcurrentLinkedQueue<AbstractMessage> exactQueue = messages.get(agentByAddress(receiver));
        exactQueue.add(message);
    }

    private void addReceiver(MessageAgent receiver) {
        messages.put(receiver, new ConcurrentLinkedQueue<>());
        receivers.put(receiver.getAddress(), receiver);
    }

    @PostConstruct
    private void init() {
        for (MessageAgent agent: agentSet) {
            addReceiver(agent);
        }
        start();
    }

    private void start() {
        for (MessageAgent receiver: receivers.values()) {
            ConcurrentLinkedQueue<AbstractMessage> messageQueue = messages.get(receiver);
            new MessageQueueWatcher(messageQueue, receiver).start();
        }
    }

    private MessageAgent agentByAddress(Address address) {
        return receivers.get(address);
    }
}
