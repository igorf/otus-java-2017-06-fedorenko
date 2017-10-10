package ru.otus.lib.app;

import java.io.IOException;

/**
 * Created by tully.
 */
public interface MsgClient {
    void send(Msg msg);

    Msg pool();

    Msg take() throws InterruptedException;

    void close() throws IOException;
}
