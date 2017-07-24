package com.otus.hw07.atm;

import com.otus.hw07.atm.commands.GetMoneyCommand;
import com.otus.hw07.atm.commands.PutMoneyCommand;
import com.otus.hw07.atm.commands.ShowAmountCommand;
import com.otus.hw07.atm.state.AtmInitialState;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AtmNonTextStyleTest {
    private Atm atm;

    @Before
    public void setUp() throws Exception {
        AtmInitialState state = new AtmInitialState();
        state.addCellState(1000, 1);
        state.addCellState(500, 1);
        state.addCellState(100, 1);
        atm = new Atm(state);
    }

    @Test
    public void runValidCommands() throws Exception {
        Map<Integer, Integer> incoming = new HashMap<>();
        incoming.put(1000, 10);
        incoming.put(500, 50);

        assertEquals("ATM amount: 1600", atm.runCommand(new ShowAmountCommand()).getMessage().trim());
        assertEquals("Cache in: OK", atm.runCommand(new PutMoneyCommand(incoming)).getMessage().trim());
        assertEquals("OK. Banknotes: 1000, 500", atm.runCommand(new GetMoneyCommand(1500)).getMessage().trim());
        assertEquals("ATM amount: 35100", atm.runCommand(new ShowAmountCommand()).getMessage().trim());
    }
}