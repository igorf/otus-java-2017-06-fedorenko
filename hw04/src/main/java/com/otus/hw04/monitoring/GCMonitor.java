package com.otus.hw04.monitoring;

import javax.management.NotificationEmitter;
import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

public class GCMonitor {
    public void install() {
        List<GarbageCollectorMXBean> gcBeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcBean;
            emitter.addNotificationListener(new CustomGCEventsListener(), null, null);
        }
    }
}
