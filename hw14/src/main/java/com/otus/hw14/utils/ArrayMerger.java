package com.otus.hw14.utils;

public class ArrayMerger {
    public static int[] merge (int[] left, int[] right) {
        int result[] = new int[left.length + right.length];
        int lIdx = 0;
        int rIdx = 0;
        for (int i = 0; i < result.length; i++) {
            if (lIdx >= left.length) {
                result[i] = right[rIdx];
                rIdx ++;
            } else if (rIdx >= right.length) {
                result[i] = left[lIdx];
                lIdx ++;
            } else if (left[lIdx] < right[rIdx]) {
                result[i] = left[lIdx];
                lIdx ++;
            } else {
                result[i] = right[rIdx];
                rIdx ++;
            }
        }
        return result;
    }
}
