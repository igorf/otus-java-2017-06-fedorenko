package com.otus.hw16.frontend.data.messages;

import lombok.Getter;
import lombok.Setter;
import ru.otus.lib.app.Msg;

import java.util.UUID;

public class LoginMessage extends Msg {

    @Getter @Setter private String userName;
    @Getter @Setter private String password;
    @Getter @Setter private UUID messageID = UUID.randomUUID();

    public LoginMessage() {
        this("", "");
    }

    public LoginMessage(String login, String password) {
        super(LoginMessage.class);
        this.userName = login;
        this.password = password;
    }
}