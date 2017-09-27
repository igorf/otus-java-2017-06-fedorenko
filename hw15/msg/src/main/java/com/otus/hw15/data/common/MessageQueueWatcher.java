package com.otus.hw15.data.common;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public class MessageQueueWatcher extends Thread {
    private ConcurrentLinkedQueue<AbstractMessage> messageQueue;
    private MessageAgent receiver;
    private final static long SLEEP_INTERVAL = 50;
    private final static Logger logger = Logger.getLogger(MessageQueueWatcher.class.getName());

    MessageQueueWatcher(ConcurrentLinkedQueue<AbstractMessage> queue, MessageAgent receiver) {
        messageQueue = queue;
        this.receiver = receiver;
    }

    private void rest(long time) {
        try {
            Thread.sleep(time);
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
                logger.severe(ex.getMessage());
            }
        }
    }

    public void run() {
        while(true) {
            long begin = System.currentTimeMillis();
            tick();
            long end = System.currentTimeMillis();
            long elapsed = end - begin;

            if (elapsed < SLEEP_INTERVAL) {
                rest(SLEEP_INTERVAL - elapsed);
            } else {
                logger.severe("[!] Queue overflow! Elapsed time: " + elapsed);
            }
        }
    }
}
