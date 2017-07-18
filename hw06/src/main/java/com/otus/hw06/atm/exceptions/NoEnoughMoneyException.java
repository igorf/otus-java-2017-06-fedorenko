package com.otus.hw06.atm.exceptions;

public class NoEnoughMoneyException extends Exception {
    public NoEnoughMoneyException() {
        super("No enough money");
    }
}
