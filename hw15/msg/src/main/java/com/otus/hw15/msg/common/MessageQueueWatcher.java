package com.otus.hw15.msg.common;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageQueueWatcher extends Thread {
    private ConcurrentLinkedQueue<Message> messageQueue;
    private MessageAgent receiver;
    private final static long SLEEP_INTERVAL = 10;

    MessageQueueWatcher(ConcurrentLinkedQueue<Message> queue, MessageAgent receiver) {
        messageQueue = queue;
        this.receiver = receiver;
    }

    private void rest() {
        try {
            Thread.sleep(SLEEP_INTERVAL);
        } catch (InterruptedException ex) {
            //Nothing to do
        }
    }

    private void tick() {
        while (!messageQueue.isEmpty()) {
            Message msg = messageQueue.poll();
            try {
                msg.run(receiver);
            } catch (Exception ex) {
                //TODO: LOG!
                ex.printStackTrace();
            }
        }
    }

    public void run() {
        while(true) {
            tick();
            rest();
        }
    }
}
