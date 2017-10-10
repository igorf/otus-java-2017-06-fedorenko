package com.otus.hw16.serverapp.reactor.reactions.client;

import com.otus.hw16.common.reactor.MessageReactor;
import com.otus.hw16.frontend.data.messages.UserFromDBMessage;
import com.otus.hw16.serverapp.common.MessagesCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.lib.app.Msg;
import ru.otus.lib.app.MsgClient;

@Component
public class UserFromDBClientReacion implements MessageReactor {

    @Autowired private MessagesCache messages;

    @Override
    public boolean isOK(Msg message) {
        return message instanceof UserFromDBMessage;
    }

    @Override
    public void react(Msg message, MsgClient client) {
        UserFromDBMessage msg = (UserFromDBMessage) message;
        MsgClient caller = messages.popCallbackSender(msg.getResponseID());
        if (caller != null) {
            caller.send(message);
        }
    }
}
