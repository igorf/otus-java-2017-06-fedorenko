package com.otus.hw16.db;

import com.otus.hw16.db.server.DatabaseServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import ru.otus.lib.starter.ServerStarter;

@Component
public class Application {
    private static final int DEFAULT_PORT = 5007;
    private static final String CONTEXT_FILE = "applicationContext.xml";
    @Autowired private DatabaseServer dbServer;

    public static void main(String ... args) throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(CONTEXT_FILE);
        Application app = ctx.getBean(Application.class);
        app.start(args);
    }

    private void start(String ... args) throws Exception {
        dbServer.setPort(definePort(args));
        ServerStarter.start(dbServer);
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
}
