package com.otus.hw16.common.client;

import com.otus.hw16.common.config.ServerConfig;
import ru.otus.lib.app.Msg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class MessagesDispatcher {
    private static final Logger logger = Logger.getLogger(MessagesDispatcher.class.getName());

    protected abstract void processIncomingMessage(MessagesClient client, Msg message);

    protected MessagesClient createClient(ServerConfig cfg) {
        try {
            MessagesClient client = new MessagesClient(cfg.getHost(), cfg.getPort());
            client.init();

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(() -> {
                try {
                    while (true) {
                        Msg msg = client.take();
                        if (msg != null) {
                            processIncomingMessage(client, msg);
                        }
                    }
                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                }
            });
            return client;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
