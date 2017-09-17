package com.otus.hw14.benchmark;

import com.otus.hw14.sorter.Sorter;

import java.util.logging.Logger;

public class MultithreadSorter implements BenchmarkSorter {
    private Logger logger = Logger.getLogger(MultithreadSorter.class.getName());
    private Sorter sorter = new Sorter();

    @Override
    public void sort(Object[] array) {
        try {
            sorter.sort(array);
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }
    }
}
