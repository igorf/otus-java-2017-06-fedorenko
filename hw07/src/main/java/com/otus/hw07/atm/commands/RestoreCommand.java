package com.otus.hw07.atm.commands;

import com.otus.hw07.atm.commands.result.BasicCommandResult;
import com.otus.hw07.atm.commands.result.CommandResult;
import com.otus.hw07.atm.parts.AtmStorage;

public class RestoreCommand implements AtmCommand {

    @Override
    public CommandResult run(AtmStorage atm) throws Exception {
        BasicCommandResult result = new BasicCommandResult();
        atm.restore();
        result.setMessage("ATM restored");
        return result;
    }
}
