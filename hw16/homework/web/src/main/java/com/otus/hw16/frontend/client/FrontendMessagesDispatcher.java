package com.otus.hw16.frontend.client;

import com.otus.hw16.common.client.MessagesClient;
import com.otus.hw16.common.client.MessagesDispatcher;
import com.otus.hw16.frontend.communication.MessageAction;
import com.otus.hw16.frontend.communication.MessagesChain;
import com.otus.hw16.frontend.data.messages.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.lib.app.Msg;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.logging.Logger;

@Component
public class FrontendMessagesDispatcher extends MessagesDispatcher {

    @Autowired MessagesChain messageChain;
    @Autowired ServerConfigWrapper messagesServerConfig;
    private static final Logger logger = Logger.getLogger(FrontendMessagesDispatcher.class.getName());
    private MessagesClient client;

    public FrontendMessagesDispatcher() {
    }

    @PostConstruct
    void init() {
        client = createClient(messagesServerConfig.getServerConfig());
    }

    @PreDestroy
    public void shutdown() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Msg message) {
        if (client != null) {
            client.send(message);
        } else {
            logger.severe("No MSG connections!");
        }
    }

    @Override
    protected void processIncomingMessage(MessagesClient client, Msg message) {
        ResponseMessage response =  (ResponseMessage) message;
        MessageAction action = messageChain.popAction(response.getResponseID());
        if (action != null) {
            action.setResponse(response);
            action.run();
        }
    }
}
