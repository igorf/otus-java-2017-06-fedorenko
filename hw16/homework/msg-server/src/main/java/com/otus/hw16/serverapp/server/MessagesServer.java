package com.otus.hw16.serverapp.server;

import com.otus.hw16.serverapp.reactor.Reactor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.lib.app.Msg;
import ru.otus.lib.app.MsgClient;
import ru.otus.lib.server.MsgServer;

@Component
public class MessagesServer extends MsgServer {
    @Autowired private Reactor reactor;

    protected void onMessage(Msg message, MsgClient client) {
        reactor.react(message, client);
    }
}