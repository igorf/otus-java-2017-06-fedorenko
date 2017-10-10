package com.otus.hw16.db.server.reactions;

import com.otus.hw16.db.server.selector.DatabaseMessageReaction;
import com.otus.hw16.db.service.UserService;
import com.otus.hw16.frontend.data.messages.FindUserByIDMessage;
import com.otus.hw16.frontend.data.messages.UserFromDBMessage;
import com.otus.hw16.frontend.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.lib.app.Msg;
import ru.otus.lib.app.MsgClient;

@Component
public class UserByIDReaction implements DatabaseMessageReaction {

    @Autowired
    UserService userService;

    @Override
    public void react(Msg message, MsgClient client) {
        FindUserByIDMessage msg = (FindUserByIDMessage) message;
        User user = userService.find(msg.getUid());
        UserFromDBMessage response = new UserFromDBMessage(user);
        response.setResponseID(msg.getMessageID());

        client.send(response);
    }

    @Override
    public boolean isOkFor(Msg message) {
        return message instanceof FindUserByIDMessage;
    }
}
