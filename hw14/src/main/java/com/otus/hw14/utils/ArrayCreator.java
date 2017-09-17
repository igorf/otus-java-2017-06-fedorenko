package com.otus.hw14.utils;

import java.util.Random;

public class ArrayCreator {
    private final static Random random = new Random();

    public static Object[] createArray(int len) {
        Object[] result = new Object[len];
        for (int i = 0; i < len; i++ ) {
            result[i] = random.nextInt(100);
        }
        return result;
    }
}
