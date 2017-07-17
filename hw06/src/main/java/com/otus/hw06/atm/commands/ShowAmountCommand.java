package com.otus.hw06.atm.commands;

import com.otus.hw06.atm.parts.AtmStorage;

public class ShowAmountCommand implements AtmCommand {

    @Override
    public String run(AtmStorage atm) throws Exception {
        return "ATM amount: " + atm.getAmount();
    }
}
