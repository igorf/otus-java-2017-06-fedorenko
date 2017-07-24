package com.otus.hw07.atm.commands;

import com.otus.hw07.atm.commands.result.CommandResult;
import com.otus.hw07.atm.commands.result.ShowAmountResult;
import com.otus.hw07.atm.parts.AtmStorage;

public class ShowAmountCommand implements AtmCommand {

    @Override
    public CommandResult run(AtmStorage atm) throws Exception {
        ShowAmountResult result = new ShowAmountResult();
        result.setAmount(atm.getAmount());
        return result;
    }
}
