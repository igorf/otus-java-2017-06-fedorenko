package com.otus.hw07.atm.exceptions;

public class NoEnoughMoneyException extends Exception {
    public NoEnoughMoneyException() {
        super("No enough money");
    }
}
