package com.otus.hw07.atm.exceptions;

public class NoBanknotesException extends Exception {
    public NoBanknotesException() {
        super("No suitable banknotes");
    }
}
