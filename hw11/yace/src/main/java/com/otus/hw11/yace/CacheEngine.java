package com.otus.hw11.yace;

public interface CacheEngine<K, V> {
    CacheContainer<K, V> get(K key);
    void put(CacheContainer<K, V> item);
    void remove(K key);
    void clean();
    long misses();
    long hits();
}
