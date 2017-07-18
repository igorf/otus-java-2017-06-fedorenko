package com.otus.hw06.atm.parts;

import com.otus.hw06.atm.Atm;
import com.otus.hw06.atm.exceptions.EmptyAtmException;
import lombok.Getter;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AtmStorage {
    private Map<Integer, AtmCurrencyCell> cells = new HashMap<>();
    private Map<Integer, AtmCurrencyCell> inCache = new HashMap<>();
    private Map<Integer, AtmCurrencyCell> outCache = new HashMap<>();
    private Logger logger = Logger.getLogger(AtmStorage.class.getName());
    @Getter private Atm atm;

    public AtmStorage(Set<Integer> denominations, Atm atm) throws Exception {
        if (atm == null) {
            throw new EmptyAtmException();
        }

        this.atm = atm;

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

    public synchronized boolean getMoneyBundle(Map<Integer, Integer> asked) {
        try {
            for (int denomination: asked.keySet()) {
                getMoneyFrom(denomination, asked.get(denomination));
            }
            commitOutput();
            return true;
        } catch (Exception ex) {
            rollbackOutput();
        }
        return false;
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
                value.clean();
            } catch (Exception e) {
                logger.log(Level.WARNING, e.getMessage());
            }
        });
    }

    private void clearCache(Map<Integer, AtmCurrencyCell> cache) {
        cache.forEach((key, value) -> value.clean());
    }
}
