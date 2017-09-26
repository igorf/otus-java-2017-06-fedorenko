package com.otus.hw15.msg.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageBroker {

    private final Map<MessageAgent, ConcurrentLinkedQueue<Message>> messages = new HashMap<>();
    private final Map<Address, MessageAgent> receivers = new HashMap<>();
    private final static long SLEEP_INTERVAL = 10;

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
            new Thread(() -> {
                while (true) {
                    ConcurrentLinkedQueue<Message> messageQueue = messages.get(receiver);
                    while (!messageQueue.isEmpty()) {
                        Message msg = messageQueue.poll();
                        try {
                            msg.run(receiver);
                        } catch (Exception ex) {
                            //TODO: LOG!
                            ex.printStackTrace();
                        }

                    }
                    try {
                        Thread.sleep(SLEEP_INTERVAL);
                    } catch (InterruptedException ex) {
                        //Nothing to do
                    }
                }
            }).start();
        }
    }

    private MessageAgent agentByAddress(Address address) {
        return receivers.get(address);
    }
}
