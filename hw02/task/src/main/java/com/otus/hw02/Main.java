package com.otus.hw02;

import com.otus.hw02.memory.MemoryChecker;
import com.otus.hw02.memory.MemoryCheckerSelector;
import com.otus.hw02.memory.MemoryCheckerType;

public class Main {
    public static void main(String args[]) {
        MemoryChecker m = MemoryCheckerSelector.getMemoryChecker(MemoryCheckerType.INSTRUMENTATION);

        System.out.println("Empty string size: " + checkSize(m, ""));
        System.out.println("Empty array size: " + checkSize(m, new int[0]));
        System.out.println("array[1] size: " + checkSize(m, new int[1]));
        System.out.println("array[2] size: " + checkSize(m, new int[2]));
        System.out.println("array[3] size: " + checkSize(m, new int[3]));

        m = MemoryCheckerSelector.getMemoryChecker(MemoryCheckerType.STREAM);

        System.out.println("Empty string size: " + checkSize(m, ""));
        System.out.println("Empty array size: " + checkSize(m, new int[0]));
        System.out.println("array[1] size: " + checkSize(m, new int[1]));
        System.out.println("array[2] size: " + checkSize(m, new int[2]));
        System.out.println("array[3] size: " + checkSize(m, new int[3]));
    }

    private static long checkSize(MemoryChecker m, Object object) {
        try {
           return m.getObjectSize(object);
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return -1;
    }
}
