package com.otus.hw16.common.reactor;

import ru.otus.lib.app.Msg;
import ru.otus.lib.app.MsgClient;

import java.util.Set;
import java.util.logging.Logger;

public abstract class AbstractReactor {
    private static final Logger logger = Logger.getLogger(AbstractReactor.class.getName());

    public void react(Msg message) {
        MessageReactor reactor = selectByMessage(message);
        if (reactor != null) {
            try {
                reactor.react(message, null);
            } catch (Exception ex) {
                logger.severe(ex.getMessage());
            }
        } else {
            logger.severe("Unknown message type: " + message.getClassName());
        }
    }

    public void react(Msg message, MsgClient client) {
        MessageReactor reactor = selectByMessage(message);
        if (reactor != null) {
            try {
                reactor.react(message, client);
            } catch (Exception ex) {
                logger.severe(ex.getMessage());
            }
        } else {
            logger.severe("Unknown message type: " + message.getClassName());
        }
    }

    protected abstract Set<MessageReactor> getReactors();

    private MessageReactor selectByMessage(Msg message) {
        for (MessageReactor r: getReactors()) {
            if (r.isOK(message)) {
                return r;
            }
        }
        return null;
    }
}
