package com.otus.hw07.atm;

import com.otus.hw07.atm.commands.AtmCommand;
import com.otus.hw07.atm.commands.AtmCommandFactory;
import com.otus.hw07.atm.commands.result.CommandResult;
import com.otus.hw07.atm.parts.AtmStorage;
import com.otus.hw07.atm.state.AtmInitialState;

import java.util.Comparator;
import java.util.List;

public class Atm {
    private AtmInitialState initialState;
    private AtmStorage storage;

    public Atm(AtmInitialState state) throws Exception {
        initialState = state;
        storage = new AtmStorage(initialState, this);
    }

    private AtmCommand getCommand(String command) throws Exception {
        return AtmCommandFactory.createCommand(command.toUpperCase().trim());
    }

    public CommandResult runCommand(String command) throws Exception {
        AtmCommand atmCommand = getCommand(command);
        return atmCommand.run(storage);
    }

    public CommandResult runCommand(AtmCommand atmCommand) throws Exception {
        return atmCommand.run(storage);
    }

    public String getGreetings() {
        String result = "Available denominations: ";
        List<Integer> denominations = storage.getAllDenominations();
        denominations.sort(Comparator.reverseOrder());
        for (int i: denominations) {
            result += (i + " ");
        }
        return result;
    }
}
