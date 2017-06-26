package com.otus.hw04.burn;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemoryBurner {
    @Setter private long objectsPerRun = 1_000_000;
    @Setter private long timeout = 3000;

    private List<MemoryBurnObject> constant = new ArrayList<>();
    private Timer timer = new Timer();
    private static final Logger logger = Logger.getLogger(MemoryBurner.class.getName());

    public void start() {
        timer.scheduleAtFixedRate(onTime(this::burn), 0, timeout);
    }

    public void stop() {
        timer.cancel();
    }

    private void burn() {
        for (int i = 0; i < objectsPerRun; i++) {
            MemoryBurnObject object = new MemoryBurnObject();
            if (i % 2 == 0) {
                constant.add(object);
            }
        }

        logger.log(Level.FINE, "Objects in constant holder: " + length());
    }

    private static TimerTask onTime(Runnable r) {
        return new TimerTask() {

            @Override
            public void run() {
                r.run();
            }
        };
    }

    public long length() {
        return constant.size();
    }
}
