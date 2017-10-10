package com.otus.hw16.common.reactor;

import ru.otus.lib.app.Msg;
import ru.otus.lib.app.MsgClient;

public interface MessageReactor {
    boolean isOK(Msg message);
    void react(Msg message, MsgClient client);
}
