package ru.otus.lib.server;

import ru.otus.lib.app.Msg;
import ru.otus.lib.app.MsgClient;
import ru.otus.lib.channel.SocketMsgClient;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import lombok.Setter;

public abstract class MsgServer implements MsgServerMBean {
    private static final Logger logger = Logger.getLogger(MsgServer.class.getName());

    private static final int THREADS_NUMBER = 1;
    private static final int DELAY = 100;

    private final ExecutorService executor;
    private final List<MsgClient> clients;

    @Setter
    private int port;

    public MsgServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        clients = new ArrayList<>();
    }

    public void start() throws Exception {
        executor.submit(this::react);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket socket = serverSocket.accept();
                SocketMsgClient client = new SocketMsgClient(socket);
                client.init();
                clients.add(client);
            }
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private Object react() throws InterruptedException {
        while (true) {
            for (MsgClient client : clients) {
                Msg msg = client.pool();
                while (msg != null) {
                    onMessage(msg, client);
                    msg = client.pool();
                }
            }
            Thread.sleep(DELAY);
        }
    }

    abstract protected void onMessage(Msg msg, MsgClient client);

    @Override
    public boolean getRunning() {
        return true;
    }

    @Override
    public void setRunning(boolean running) {
        if (!running) {
            executor.shutdown();
        }
    }
}
