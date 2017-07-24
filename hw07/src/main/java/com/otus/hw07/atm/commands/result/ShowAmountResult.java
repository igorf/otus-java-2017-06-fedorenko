package com.otus.hw07.atm.commands.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public final class ShowAmountResult extends BasicCommandResult {
    @Getter @Setter private int amount;

    public void setMessage(String message) {
        throw new IllegalArgumentException();
    }

    @Override
    public String getMessage() {
        return "ATM amount: " + amount;
    }
}
