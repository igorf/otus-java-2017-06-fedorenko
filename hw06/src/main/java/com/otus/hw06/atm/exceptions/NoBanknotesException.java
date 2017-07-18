package com.otus.hw06.atm.exceptions;

public class NoBanknotesException extends Exception {
    public NoBanknotesException() {
        super("No suitable banknotes");
    }
}
