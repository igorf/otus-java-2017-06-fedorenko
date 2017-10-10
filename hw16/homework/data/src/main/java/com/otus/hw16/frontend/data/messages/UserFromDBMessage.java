package com.otus.hw16.frontend.data.messages;

import com.otus.hw16.frontend.data.model.User;
import lombok.Getter;
import lombok.Setter;
import ru.otus.lib.app.Msg;

import java.util.UUID;

public class UserFromDBMessage extends Msg implements ResponseMessage {

    @Getter @Setter private User user;
    @Getter @Setter private UUID responseID;

    public UserFromDBMessage() {
        this(null);
    }

    public UserFromDBMessage(User user) {
        super(UserFromDBMessage.class);
        this.user = user;
    }
}