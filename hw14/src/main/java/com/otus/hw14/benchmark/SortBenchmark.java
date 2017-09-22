package com.otus.hw14.benchmark;

import com.otus.hw14.utils.ArrayCreator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class SortBenchmark {
    private static final int ARRAY_SIZE = 5_000_000;
    private static final int RUN_COUNT = 10;

    @Getter
    private List<Double> results = new ArrayList<>();

    public void mark(BenchmarkSorter sorter) {
        results.clear();
        for (int i = 0; i < RUN_COUNT; i++) {
            int[] unsorted = ArrayCreator.createArray(ARRAY_SIZE);
            double before = System.nanoTime();
            sorter.sort(unsorted);
            double after = System.nanoTime();

            results.add(after - before);
        }
    }

    public double getAverageNanotime() {
        if (results.size() == 0) {
            return 0;
        }
        return results.stream().reduce((s1, s2) -> s1 + s2).orElse((double) 0) / results.size();
    }
}
