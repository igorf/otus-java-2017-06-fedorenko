package com.otus.hw12;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {

    private static final int DEFAULT_PORT = 8089;

    public static void main(String... args) throws Exception {
        Server server = new Server(definePort(args));

        String rootPath = Main.class.getClassLoader().getResource("webapp").toString();
        WebAppContext webapp = new WebAppContext(rootPath, "");
        server.setHandler(webapp);

        server.start();
        server.join();
    }

    private static int definePort(String... args) {
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
}
