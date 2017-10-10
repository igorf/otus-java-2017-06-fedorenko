package com.otus.hw16.serverapp.client;

import com.otus.hw16.common.client.MessagesClient;
import com.otus.hw16.common.client.MessagesDispatcher;
import com.otus.hw16.common.config.ServerConfig;
import com.otus.hw16.serverapp.reactor.Reactor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.lib.app.Msg;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Component
public class DatabaseMessagesDispatcher extends MessagesDispatcher {
    @Autowired private Reactor reactor;
    private static final Logger logger = Logger.getLogger(DatabaseMessagesDispatcher.class.getName());
    private static final String DB_CONFIG_NAME = "database.json";
    private static final Random random = new Random();

    private List<MessagesClient> clients = new ArrayList<>();

    public DatabaseMessagesDispatcher() {
        for (ServerConfig cfg: ServerConfig.readFromConfigFile(DB_CONFIG_NAME)) {
            MessagesClient client = createClient(cfg);
            if (client != null) {
                clients.add(client);
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        for (MessagesClient client: clients) {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(Msg message) {
        MessagesClient client = findClient();
        if (client != null) {
            client.send(message);
        } else {
            logger.severe("No DB connections!");
        }
    }

    private MessagesClient findClient() {
        return clients.get(random.nextInt(clients.size()));
    }

    @Override
    protected void processIncomingMessage(MessagesClient client, Msg message) {
        reactor.react(message);
    }
}
