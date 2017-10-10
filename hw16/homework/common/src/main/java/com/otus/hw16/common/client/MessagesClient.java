package com.otus.hw16.common.client;

import ru.otus.lib.channel.SocketMsgClient;

import java.io.IOException;
import java.net.Socket;

public class MessagesClient extends SocketMsgClient {
    private final Socket socket;

    public MessagesClient(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    private MessagesClient(Socket socket) throws IOException {
        super(socket);
        this.socket = socket;
    }

    public void close() throws IOException {
        super.close();
        socket.close();
    }
}
