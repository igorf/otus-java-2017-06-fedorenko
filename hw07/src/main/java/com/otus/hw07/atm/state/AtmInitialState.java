package com.otus.hw07.atm.state;

import java.util.HashMap;
import java.util.Map;

public class AtmInitialState {
    private Map<Integer, Integer> cells;

    public AtmInitialState(Map<Integer, Integer> cells) {
        this.cells = cells;
    }

    public AtmInitialState() {
        this(new HashMap<>());
    }

    public void addCellState(int denomination, int banknotes) {
        cells.put(denomination, banknotes);
    }

    public Map<Integer, Integer> getCells() {
        return cells;
    }
}
