package com.otus.hw15.msg.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageBroker {

    private final Map<MessageAgent, ConcurrentLinkedQueue<Message>> messages = new HashMap<>();
    private final Map<Address, MessageAgent> receivers = new HashMap<>();

    public MessageBroker(List<MessageAgent> agents) {
        for (MessageAgent agent: agents) {
            addReceiver(agent);
        }
        start();
    }

    public void sendMessage(Address receiver, Message message) {
        ConcurrentLinkedQueue<Message> exactQueue = messages.get(agentByAddress(receiver));
        exactQueue.add(message);
    }

    private void addReceiver(MessageAgent receiver) {
        messages.put(receiver, new ConcurrentLinkedQueue<>());
        receivers.put(receiver.getAddress(), receiver);
    }

    private void start() {
        for (MessageAgent receiver: receivers.values()) {
            ConcurrentLinkedQueue<Message> messageQueue = messages.get(receiver);
            new MessageQueueWatcher(messageQueue, receiver).start();
        }
    }

    private MessageAgent agentByAddress(Address address) {
        return receivers.get(address);
    }
}
