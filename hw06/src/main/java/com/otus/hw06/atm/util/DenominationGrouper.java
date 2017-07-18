package com.otus.hw06.atm.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DenominationGrouper {
    public static Map<Integer, Integer> group(Collection<Integer> banknotes) {
        Map<Integer, Integer> result = new HashMap<>();
        banknotes.forEach(v -> {
            result.put(v, (result.keySet().contains(v) ? result.get(v) + 1 : 1));
        });
        return result;
    }
}
