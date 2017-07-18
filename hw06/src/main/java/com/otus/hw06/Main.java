package com.otus.hw06;

import com.otus.hw06.atm.Atm;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());
        Scanner scanner = new Scanner(System.in);

        Set<Integer> banknoteDenominations = new HashSet<>();
        banknoteDenominations.add(100);
        banknoteDenominations.add(500);
        banknoteDenominations.add(1000);

        try {
            Atm atm = new Atm(banknoteDenominations);
            System.out.println(atm.getGreetings());
            while (true) {
                System.out.println(atm.runCommand(scanner.nextLine()));
            }
        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
        }
    }
}
