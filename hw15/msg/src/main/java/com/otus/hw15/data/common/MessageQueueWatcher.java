package com.otus.hw15.data.common;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageQueueWatcher extends Thread {
    private ConcurrentLinkedQueue<AbstractMessage> messageQueue;
    private MessageAgent receiver;
    private final static long SLEEP_INTERVAL = 10;

    MessageQueueWatcher(ConcurrentLinkedQueue<AbstractMessage> queue, MessageAgent receiver) {
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
            AbstractMessage msg = messageQueue.poll();
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
