package com.otus.hw16.frontend.data.messages;

import lombok.Getter;
import lombok.Setter;
import ru.otus.lib.app.Msg;

import java.util.UUID;

public class FindUserByIDMessage extends Msg {

    @Getter @Setter private int uid;
    @Getter @Setter private UUID messageID = UUID.randomUUID();

    public FindUserByIDMessage() {
        this(-1);
    }

    public FindUserByIDMessage(int uid) {
        super(FindUserByIDMessage.class);
        this.uid = uid;
    }
}