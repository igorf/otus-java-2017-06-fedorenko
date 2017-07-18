package com.otus.hw06.atm.exceptions;

public class EmptyAtmException extends Exception {
    public EmptyAtmException() {
        super("Atm storage must be part of ATM");
    }
}
