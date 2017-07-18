package com.otus.hw06.atm;


import com.otus.hw06.atm.commands.AtmCommand;
import com.otus.hw06.atm.commands.AtmCommandFactory;
import com.otus.hw06.atm.parts.AtmStorage;

import java.util.Set;

public class Atm {
    private AtmStorage storage;
    public Atm(Set<Integer> denominations) throws Exception {
        storage = new AtmStorage(denominations);
    }

    private AtmCommand getCommand(String command) {
        return AtmCommandFactory.createCommand(command.toUpperCase().trim());
    }

    public String runCommand(String command) {
        AtmCommand atmCommand = getCommand(command);
        if (atmCommand != null) {
            try {
                return atmCommand.run(storage);
            } catch (Exception e) {
                return "ATM error: " + e.getMessage();
            }
        }
        return "Unknown command";
    }

    public String getGreetings() {
        String result = "Available denominations: ";
        for (int i: storage.getAllDenominations()) {
            result += i + " ";
        }
        return result;
    }
}
