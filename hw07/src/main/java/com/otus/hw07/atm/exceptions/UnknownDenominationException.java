package com.otus.hw07.atm.exceptions;

public class UnknownDenominationException extends Exception {
    public UnknownDenominationException() {
        super("Unknown currency denomination");
    }
}
