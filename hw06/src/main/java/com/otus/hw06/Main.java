package com.otus.hw06;

import com.otus.hw06.atm.Atm;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());

        Set<Integer> banknoteDenominations = new HashSet<>();
        banknoteDenominations.add(100);
        banknoteDenominations.add(500);
        banknoteDenominations.add(1000);

        try {
            Atm atm = new Atm(banknoteDenominations);
            System.out.println(atm.runCommand("AMOUNT"));
            System.out.println(atm.runCommand("PUT 100:5 500:100 1000:100"));
            System.out.println(atm.runCommand("AMOUNT"));
            System.out.println(atm.runCommand("GET 200"));
            System.out.println(atm.runCommand("AMOUNT"));
            System.out.println(atm.runCommand("GET 400"));
            System.out.println(atm.runCommand("AMOUNT"));
            System.out.println(atm.runCommand("GET 30000"));
            System.out.println(atm.runCommand("AMOUNT"));
        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
        }
    }
}
