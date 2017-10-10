package com.otus.hw16.db.server.selector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.lib.app.Msg;

import java.util.Set;

@Component
public class ReactionSelector {

    @Autowired private Set<DatabaseMessageReaction> reactions;

    public DatabaseMessageReaction getReaction(Msg message) {
        for (DatabaseMessageReaction r: reactions) {
            if (r.isOkFor(message)) {
                return r;
            }
        }
        return null;
    }
}