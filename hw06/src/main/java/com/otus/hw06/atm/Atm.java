package com.otus.hw06.atm;


import com.otus.hw06.atm.commands.AtmCommand;
import com.otus.hw06.atm.commands.AtmCommandFactory;
import com.otus.hw06.atm.parts.AtmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class Atm {
    private AtmStorage storage;

    public Atm(Set<Integer> denominations) throws Exception {
        storage = new AtmStorage(denominations, this);
    }

    private AtmCommand getCommand(String command) throws Exception {
        return AtmCommandFactory.createCommand(command.toUpperCase().trim());
    }

    public String runCommand(String command) {
        try {
            AtmCommand atmCommand = getCommand(command);
            return atmCommand.run(storage);
        } catch (Exception e) {
            return "ATM error: " + e.getMessage();
        }
    }

    public String getGreetings() {
        String result = "Available denominations: ";
        List<Integer> denominations = storage.getAllDenominations();
        denominations.sort(Comparator.reverseOrder());
        for (int i: denominations) {
            result += i + " ";
        }
        return result;
    }
}
