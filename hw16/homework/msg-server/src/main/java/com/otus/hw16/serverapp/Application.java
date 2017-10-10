package com.otus.hw16.serverapp;

import com.otus.hw16.serverapp.server.MessagesServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ru.otus.lib.starter.ServerStarter;

@Component
public class Application {

    private static final int DEFAULT_PORT = 5007;
    @Autowired private MessagesServer messagesServer;

    private void start(String ... args) throws Exception {
        messagesServer.setPort(definePort(args));
        ServerStarter.start(messagesServer);
    }

    private static int definePort(String ... args) {
        int port;
        if (args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (Exception ex) {
                port = DEFAULT_PORT;
            }
        } else {
            port = DEFAULT_PORT;
        }

        return port;
    }

    public static void main(String ... args) throws Exception {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("com.otus.hw16.serverapp");
        Application app = ctx.getBean(Application.class);
        app.start(args);
    }
}