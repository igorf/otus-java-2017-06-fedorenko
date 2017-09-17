package com.otus.hw14;

import com.otus.hw14.benchmark.MultithreadSorter;
import com.otus.hw14.benchmark.NativeSorter;
import com.otus.hw14.benchmark.SortBenchmark;

import java.util.logging.Logger;


public class Main {
    private final static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String ... args) {
        SortBenchmark benchmark = new SortBenchmark();

        logger.info("Benchmark for native sort");
        benchmark.mark(new NativeSorter());
        logger.info("Average time: " + benchmark.getAverageNanotime() + " nanoseconds");

        logger.info("Benchmark for multithread sort");
        benchmark.mark(new MultithreadSorter());
        logger.info("Average time: " + benchmark.getAverageNanotime() + " nanoseconds");
    }
}