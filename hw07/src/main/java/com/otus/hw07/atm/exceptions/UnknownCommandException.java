package com.otus.hw07.atm.exceptions;

public class UnknownCommandException extends Exception {
    public UnknownCommandException(String command) {
        super("Unknown command <<" + command + ">>");
    }
}
