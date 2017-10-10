package ru.otus.lib.starter;

import ru.otus.lib.server.MsgServer;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

public class ServerStarter {
    private final static String  DEFAULT_MBEAN_NAME = "otus.lib:type=MSG_SERVER";
    private static final Logger logger = Logger.getLogger(ServerStarter.class.getName());

    public static void start(MsgServer server, String serverBeanName) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName(serverBeanName);
        mbs.registerMBean(server, name);
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.submit(() -> {
            try {
                server.start();
            } catch (Exception ex) {
                logger.severe(ex.getMessage());
            }
        });
    }

    public static void start(MsgServer server) throws Exception {
        start(server, DEFAULT_MBEAN_NAME);
    }
}
