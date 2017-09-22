package com.otus.hw14.utils;

import java.util.Random;

public class ArrayCreator {
    private final static Random random = new Random();

    public static int[] createArray(int len) {
        int[] result = new int[len];
        for (int i = 0; i < len; i++ ) {
            result[i] = random.nextInt(100);
        }
        return result;
    }
}
