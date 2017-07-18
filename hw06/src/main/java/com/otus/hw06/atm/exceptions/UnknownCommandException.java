package com.otus.hw06.atm.exceptions;

public class UnknownCommandException extends Exception {
    public UnknownCommandException(String command) {
        super("Unknown command <<" + command + ">>");
    }
}
