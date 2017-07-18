package com.otus.hw06.atm;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class AtmTest {
    private Atm atm;

    @Before
    public void setUp() throws Exception {
        Set<Integer> denominations = new HashSet<>();
        denominations.add(1000);
        denominations.add(500);
        denominations.add(100);
        atm = new Atm(denominations);
    }

    @Test
    public void runCommand() throws Exception {
        assertEquals("ATM amount: 0", atm.runCommand("amount"));
        assertEquals("Cache in: OK", atm.runCommand("put 1000:10 500:50"));
        assertEquals("ATM error: Unknown currency nominal", atm.runCommand("put 200:10"));
        assertEquals("ATM error: Unknown command <<TAKE 200>>", atm.runCommand("take 200"));
        assertEquals("OK. Banknotes: 1000, 500", atm.runCommand("get 1500").trim());
        assertEquals("ATM error: No enough money", atm.runCommand("get 1000000").trim());
        assertEquals("ATM error: No suitable banknotes", atm.runCommand("get 200").trim());
        assertEquals("ATM amount: 33500", atm.runCommand("amount").trim());
    }

    @Test
    public void getGreetings() throws Exception {
        assertEquals("Available denominations: 1000 500 100 ", atm.getGreetings());
    }
}