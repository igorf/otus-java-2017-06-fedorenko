package com.otus.hw06.atm.util;

import java.util.*;
import java.util.stream.Collectors;

public class AmountSplitter {
    private int currentAmount = 0;
    private List<Integer> denominations = new ArrayList<>(0);
    private List<List<Integer>> result = new ArrayList<>(0);
    private Map<Integer, Integer> cache = new HashMap<>(0);

    public AmountSplitter(List<Integer> denominations) {
        this.denominations = denominations.stream().distinct().collect(Collectors.toList());
        this.denominations.sort(Comparator.reverseOrder());
    }

    public synchronized List<List<Integer>> split(int amount) {
        currentAmount = amount;
        result.clear();
        cache.clear();
        doSplit(amount, 0);
        result.sort(Comparator.comparingInt(List::size));
        return result;
    }

    private void doSplit(int amount, int banknotes) {
        if (amount <= 0) {
            if (cache.values().stream().mapToInt(Integer::intValue).sum() == currentAmount) {
                List<Integer> composition = new ArrayList<>(cache.values());
                composition.sort(Integer::compareTo);
                if (!result.contains(composition)) {
                    this.result.add(composition);
                }
            }
        } else {
            for (int i: this.denominations) {
                cache.put(banknotes, i);
                doSplit(amount - i, banknotes + 1);
            }
        }
    }
}
