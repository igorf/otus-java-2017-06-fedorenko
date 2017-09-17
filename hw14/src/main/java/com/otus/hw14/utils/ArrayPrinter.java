package com.otus.hw14.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ArrayPrinter {
    public static String arrayToString(Object[] array) {
        return Arrays.stream(array)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}
