package com.otus.hw14.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ArrayPrinter {
    public static String arrayToString(int[] array) {
        return Arrays.stream(array)
                .mapToObj(i -> ((Integer) i).toString())
                .collect(Collectors.joining(", "));
    }
}
