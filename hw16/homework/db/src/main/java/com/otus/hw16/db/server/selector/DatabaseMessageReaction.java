package com.otus.hw16.db.server.selector;

import ru.otus.lib.app.Msg;
import ru.otus.lib.app.MsgClient;

public interface DatabaseMessageReaction {
    void react(Msg message, MsgClient client);
    boolean isOkFor(Msg message);
}
