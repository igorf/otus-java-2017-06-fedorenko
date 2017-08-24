package com.otus.hw11.yace;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class YaceCacheEngine<K, V> implements CacheEngine<K, V> {
    private static Logger logger = Logger.getLogger(YaceCacheEngine.class.getName());
    private final static int USAGE_CHECK_INTERVAL = 10;

    private long hits = 0;
    private long misses = 0;
    private Map<K, SoftReference<CacheContainer<K, V>>> data = new HashMap<>();
    private Map<K, Long> dataUsage = new HashMap<>();
    private int cacheSize = 0;
    private long globalTTL = 0;
    private long usageTTL = 0;
    private Timer timer = new Timer();
    private final Lock cleanLock = new ReentrantLock();

    public static<K, V> YaceCacheEngine<K, V> createSizedCache(int size) {
        return new YaceCacheEngine<>(size, 0, 0);
    }

    public static<K, V> YaceCacheEngine<K, V> createSizedCache(int size, long globalTTL) {
        return new YaceCacheEngine<>(size, globalTTL, 0);
    }

    public static<K, V> YaceCacheEngine<K, V> createSizedCache(int size, long globalTTL, long usageTTL) {
        return new YaceCacheEngine<>(size, globalTTL, usageTTL);
    }

    public static<K, V> YaceCacheEngine<K, V> createTimedCache(long globalTTL, long useTTL) {
        return new YaceCacheEngine<>(0, globalTTL, useTTL);
    }

    private YaceCacheEngine(int cacheSize, long globalTTL, long usageTTL) {
        this.cacheSize = cacheSize;
        this.globalTTL = globalTTL;
        this.usageTTL = usageTTL;
        if (this.usageTTL > 0) {
            timer.schedule(wrap(this::cleanExpiredData), USAGE_CHECK_INTERVAL, USAGE_CHECK_INTERVAL);
        }
    }

    @Override
    public CacheContainer<K, V> get(K key) {
        if (data.containsKey(key)) {
            dataUsage.put(key, getTimestamp());
            hit();
            return data.get(key).get();
        }
        miss();
        return null;
    }

    @Override
    public void put(CacheContainer<K, V> item) {
        SoftReference<CacheContainer<K, V>> reference = new SoftReference<>(item);

        if (cacheSize > 0 && data.size() >= cacheSize) {
            reduceBySizePolicy();
        }
        K key = item.getKey();
        if (globalTTL > 0) {
            timer.schedule(wrap(() -> cleanByKey(key)), globalTTL);
        }
        data.put(key, reference);
        dataUsage.put(key, getTimestamp());
    }

    @Override
    public void remove(K key) {
        cleanByKey(key);
    }

    @Override
    public void clean() {
        misses = 0;
        hits = 0;
        data.clear();
        dataUsage.clear();
    }

    @Override
    public long misses() {
        return misses;
    }

    @Override
    public long hits() {
        return hits;
    }

    private void hit() {
        hits ++;
    }

    private void miss() {
        misses ++;
    }

    protected long getTimestamp() {
        return System.currentTimeMillis();
    }

    private K getLastUsedElementKey() {
        Long oldest = Collections.min(dataUsage.values());
        for (K index: dataUsage.keySet()) {
            if (dataUsage.get(index).equals(oldest)) {
                return index;
            }
        }
        if (dataUsage.keySet().size() > 0) {
            return dataUsage.keySet().iterator().next();
        }
        return null;
    }

    private void reduceBySizePolicy() {
        K key = getLastUsedElementKey();
        if (key != null) {
            cleanByKey(key);
        }
    }

    private void cleanByKey(K key) {
        logger.log(Level.FINE, "Removing data record: " + key.toString());
        if (data.containsKey(key)) {
            data.remove(key);
        }
        if (dataUsage.containsKey(key)) {
            dataUsage.remove(key);
        }
    }

    private void cleanExpiredData() {
        if (cleanLock.tryLock()) {
            long now = getTimestamp();
            try {
                List<K> keys = new ArrayList<>();
                for (K key: dataUsage.keySet()) {
                    if (dataUsage.get(key) < now - usageTTL) {
                        keys.add(key);
                    }
                }
                for (K key: keys) {
                    cleanByKey(key);
                }
            } finally {
                cleanLock.unlock();
            }
        }
    }

    private static TimerTask wrap(Runnable r) {
        return new TimerTask() {

            @Override
            public void run() {
                r.run();
            }
        };
    }
}
