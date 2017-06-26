package com.otus.hw04.monitoring;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.util.logging.Logger;

public class CustomGCEventsListener implements NotificationListener {
    private Logger logger = Logger.getLogger(CustomGCEventsListener.class.getName());
    private SimplifiedGCInfo gcInfo = new SimplifiedGCInfo();

    @Override
    public void handleNotification(Notification notification, Object handback) {
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

            long duration = info.getGcInfo().getDuration();
            gcInfo.defineGenByAction(info.getGcAction());
            gcInfo.setName(info.getGcName());

            logger.info("GC: " + gcInfo.toString() + ", Duration: " + duration + "ms");
        }
    }
}
