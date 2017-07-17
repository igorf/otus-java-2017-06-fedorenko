package com.otus.hw06.atm.parts;

import java.util.*;

public class AtmStorage {
    private Map<Integer, AtmCurrencyCell> cells = new HashMap<>();
    private Map<Integer, AtmCurrencyCell> inCache = new HashMap<>();
    private Map<Integer, AtmCurrencyCell> outCache = new HashMap<>();

    public AtmStorage(Set<Integer> nominals) throws Exception {
        for (int nominal: nominals) {
            AtmCurrencyCell cell = new AtmCurrencyCell(nominal);
            cells.put(nominal, cell);

            cell = new AtmCurrencyCell(nominal);
            inCache.put(nominal, cell);

            cell = new AtmCurrencyCell(nominal);
            outCache.put(nominal, cell);
        }
    }

    public void putMoneyTo(int nominal, int amount) throws Exception {
        AtmCurrencyCell cell = inCache.get(nominal);
        if (cell == null) {
            throw new Exception("Unknown currency nominal");
        }
        cell.add(amount);
    }

    public int getAmount() {
        Integer amount = 0;
        for (Integer key: cells.keySet()) {
            amount += cells.get(key).amount();
        }
        return amount;
    }

    public synchronized void getMoneyFrom(int nominal, int amount) throws Exception {
        AtmCurrencyCell cell = cells.get(nominal);
        if (cell == null) {
            throw new Exception("Unknown currency nominal");
        }
        AtmCurrencyCell cache = outCache.get(nominal);
        cell.take(amount);
        cache.add(amount);
    }

    public List<Integer> getAvailableNominals() {
        List<Integer> nominals = new ArrayList<>();
        cells.values().forEach((cell) -> {
            if (cell.amount() > 0) {
                nominals.add(cell.getNominal());
            }
        });
        return nominals;
    }

    private synchronized void putFromCacheToConstantCells(Map<Integer, AtmCurrencyCell> cache) {
        cache.forEach((key, value) -> {
            try {
                cells.get(key).add(value.getQuantity());
            } catch (Exception e) {
                rollbackInput();
                e.printStackTrace();
            }
        });
    }

    private void clearCache(Map<Integer, AtmCurrencyCell> cache) {
        cache.forEach((key, value) -> value.clean());
    }

    public void commitInput() {
        putFromCacheToConstantCells(inCache);
    }

    public void rollbackInput() {
        clearCache(inCache);
    }

    public void commitOutput() {
        clearCache(outCache);
    }

    public void rollbackOutput() {
        putFromCacheToConstantCells(outCache);
    }
}
