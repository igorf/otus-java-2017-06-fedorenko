package com.otus.hw06.atm.util;

import java.util.*;
import java.util.stream.Collectors;

public class AmountSplitter {
    private List<Integer> denominations = new ArrayList<>(0);
    private List<List<Integer>> result = new ArrayList<>(0);

    public AmountSplitter(List<Integer> denominations) {
        this.denominations = denominations.stream().distinct().collect(Collectors.toList());
        this.denominations.sort(Comparator.reverseOrder());
    }

    public synchronized List<List<Integer>> split(int amount) {
        result.clear();
        doSplit(amount, new ArrayList<>(this.denominations), new ArrayList<>());
        result.sort(Comparator.comparingInt(List::size));
        return result;
    }

    private void doSplit(int amount, List<Integer> denominations, List<Integer> cache) {
        if (denominations.size() == 0 || amount <= 0) {
            if (amount == 0) {
                this.result.add(new ArrayList<>(cache));
            }
            return;
        }

        int d = denominations.get(0);
        List<Integer> next = new ArrayList<>(denominations);
        next.remove(0);
        while (amount >= 0) {
            doSplit(amount, next, new ArrayList<>(cache));
            cache.add(d);
            amount -= d;
        }
    }
}
