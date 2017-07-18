package com.otus.hw06.atm.parts;

import java.util.*;

public class AtmStorage {
    private Map<Integer, AtmCurrencyCell> cells = new HashMap<>();
    private Map<Integer, AtmCurrencyCell> inCache = new HashMap<>();
    private Map<Integer, AtmCurrencyCell> outCache = new HashMap<>();

    public AtmStorage(Set<Integer> denominations) throws Exception {
        for (int nominal: denominations) {
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

    public List<Integer> getAvailableDenominations() {
        return collectDenominations((v) -> v.amount() > 0);
    }

    public List<Integer> getAllDenominations() {
        return collectDenominations((v) -> true);
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

    interface CellCollectChecker {
        boolean isOkForCollect(AtmCurrencyCell cell);
    }

    private List<Integer> collectDenominations(CellCollectChecker checker) {
        List<Integer> denominations = new ArrayList<>();
        cells.values().forEach((cell) -> {
            if (checker.isOkForCollect(cell)) {
                denominations.add(cell.getNominal());
            }
        });
        return denominations;
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
}
