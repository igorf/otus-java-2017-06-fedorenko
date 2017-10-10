package ru.otus.lib.starter;

import ru.otus.lib.runner.ProcessRunnerImpl;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExternalAppStarter {
    private static final Logger logger = Logger.getLogger(ExternalAppStarter.class.getName());

    public static void startDelayed(String application, int delay) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(application);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }, delay, TimeUnit.SECONDS);
        executorService.shutdown();
    }
 }
