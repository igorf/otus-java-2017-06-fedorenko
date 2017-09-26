package com.otus.hw15.msg.common;

import java.util.UUID;

public class Address {
    private final UUID value = UUID.randomUUID();

    public int hashCode() {
        return value.hashCode();
    }

    public String toString() {
        return value.toString();
    }
}
