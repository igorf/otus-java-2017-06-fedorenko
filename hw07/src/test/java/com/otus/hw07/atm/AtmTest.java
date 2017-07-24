package com.otus.hw07.atm;

import com.otus.hw07.atm.commands.result.ShowAmountResult;
import com.otus.hw07.atm.exceptions.NoBanknotesException;
import com.otus.hw07.atm.exceptions.NoEnoughMoneyException;
import com.otus.hw07.atm.exceptions.UnknownCommandException;
import com.otus.hw07.atm.exceptions.UnknownDenominationException;
import com.otus.hw07.atm.state.AtmInitialState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtmTest {
    private Atm atm;

    @Before
    public void setUp() throws Exception {
        AtmInitialState state = new AtmInitialState();
        state.addCellState(1000, 10);
        state.addCellState(500, 10);
        state.addCellState(100, 10);
        atm = new Atm(state);
    }

    @Test
    public void runValidCommands() throws Exception {
        assertEquals("ATM amount: 16000", atm.runCommand("amount").getMessage().trim());
        assertEquals("Cache in: OK", atm.runCommand("put 1000:10 500:50").getMessage().trim());
        assertEquals("OK. Banknotes: 1000, 500", atm.runCommand("get 1500").getMessage().trim());
        assertEquals("ATM amount: 49500", atm.runCommand("amount").getMessage().trim());
    }

    @Test
    public void testRestore() throws Exception {
        assertEquals(16000, ((ShowAmountResult) atm.runCommand("amount")).getAmount());
        atm.runCommand("put 1000:1");
        assertEquals(17000, ((ShowAmountResult) atm.runCommand("amount")).getAmount());
        atm.runCommand("restore");
        assertEquals(16000, ((ShowAmountResult) atm.runCommand("amount")).getAmount());
    }

    @Test(expected = UnknownDenominationException.class)
    public void runUnknownCurrency() throws Exception {
        atm.runCommand("put 200:10");
    }

    @Test(expected = UnknownCommandException.class)
    public void runUnknownCommand() throws Exception {
        atm.runCommand("take 200");
    }

    @Test(expected = NoEnoughMoneyException.class)
    public void runNoEnoughMoney() throws Exception {
        atm.runCommand("get 1000000");
    }

    @Test(expected = NoBanknotesException.class)
    public void runNoBanknotes() throws Exception {
        atm.runCommand("put 1000:10");
        atm.runCommand("get 120");
    }

    @Test
    public void getGreetings() throws Exception {
        assertEquals("Available denominations: 1000 500 100 ", atm.getGreetings());
    }
}