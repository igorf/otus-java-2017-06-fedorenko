package com.otus.hw06.atm;


import com.otus.hw06.atm.commands.AtmCommand;
import com.otus.hw06.atm.commands.AtmCommandFactory;
import com.otus.hw06.atm.parts.AtmStorage;

import java.util.Set;

public class Atm {
    private AtmStorage storage;
    public Atm(Set<Integer> nominals) throws Exception {
        storage = new AtmStorage(nominals);
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
}
