package com.otus.hw16.db.server;

import com.otus.hw16.db.server.selector.DatabaseMessageReaction;
import com.otus.hw16.db.server.selector.ReactionSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.lib.app.Msg;
import ru.otus.lib.app.MsgClient;
import ru.otus.lib.server.MsgServer;

import java.util.logging.Logger;

@Component
public class DatabaseServer extends MsgServer {

    @Autowired private ReactionSelector reactionSelector;
    private final Logger logger = Logger.getLogger(DatabaseServer.class.getName());

    public DatabaseServer() {
        super();
    }

    protected void onMessage(Msg message, MsgClient client) {
        if (message == null) {
            return;
        }

        try {
            DatabaseMessageReaction reaction = reactionSelector.getReaction(message);
            if (reaction != null) {
                reaction.react(message, client);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }
    }
}