package com.otus.hw16.serverapp.reactor.reactions.server;

import com.otus.hw16.common.reactor.MessageReactor;
import com.otus.hw16.frontend.data.messages.FindUserByIDMessage;
import com.otus.hw16.serverapp.client.DatabaseMessagesDispatcher;
import com.otus.hw16.serverapp.common.MessagesCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.lib.app.Msg;
import ru.otus.lib.app.MsgClient;

@Component
public class FindUserByIDMessageReaction implements MessageReactor {

    @Autowired private MessagesCache messages;
    @Autowired private DatabaseMessagesDispatcher messagesDispatcher;

    @Override
    public boolean isOK(Msg message) {
        return message instanceof FindUserByIDMessage;
    }

    @Override
    public void react(Msg message, MsgClient client) {
        FindUserByIDMessage msg = (FindUserByIDMessage) message;
        messages.pushSender(msg.getMessageID(), client);
        messagesDispatcher.sendMessage(message);
    }
}
