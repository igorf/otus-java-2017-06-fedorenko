package com.otus.hw.yace;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class YaceCacheEngineTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void syntheticSizeTest() throws Exception {
        CacheEngine<Integer, Integer> cache = YaceCacheEngine.createSizedCache(5);
        cache.put(new CacheContainer<>(1, 1));
        cache.put(new CacheContainer<>(2, 2));
        cache.put(new CacheContainer<>(3, 3));
        cache.put(new CacheContainer<>(4, 4));
        cache.put(new CacheContainer<>(5, 5));
        cache.put(new CacheContainer<>(6, 6));

        assertNull(cache.get(1));
        assertNotNull(cache.get(2));
        assertNotNull(cache.get(3));
        assertNotNull(cache.get(4));
        assertNotNull(cache.get(5));
        assertNotNull(cache.get(6));
        assertNull(cache.get(7));
    }

    @Test
    public void syntheticTimeTest() throws Exception {
        CacheEngine<Integer, Integer> cache = YaceCacheEngine.createTimedCache(2000, 500);
        cache.put(new CacheContainer<>(1, 1));
        cache.put(new CacheContainer<>(2, 1));
        cache.put(new CacheContainer<>(3, 1));
        cache.put(new CacheContainer<>(4, 1));
        cache.put(new CacheContainer<>(5, 1));

        assertNotNull(cache.get(1));
        assertNotNull(cache.get(2));
        assertNotNull(cache.get(3));
        assertNotNull(cache.get(4));
        assertNotNull(cache.get(5));

        Thread.sleep(100);
        assertNotNull(cache.get(1));
        assertNotNull(cache.get(2));

        Thread.sleep(450);
        assertNotNull(cache.get(1));
        assertNotNull(cache.get(2));

        assertNull(cache.get(3));
        assertNull(cache.get(4));
        assertNull(cache.get(5));

        Thread.sleep(2100);
        assertNull(cache.get(1));
        assertNull(cache.get(2));
    }
}