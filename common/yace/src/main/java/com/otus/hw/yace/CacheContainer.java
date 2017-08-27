package com.otus.hw.yace;

public class CacheContainer<K, V> {
    private K key;
    private V value;

    public CacheContainer(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
