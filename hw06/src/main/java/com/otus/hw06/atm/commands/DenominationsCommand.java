package com.otus.hw06.atm.commands;

import com.otus.hw06.atm.parts.AtmStorage;

import java.util.List;

public class DenominationsCommand implements AtmCommand {
    private AtmStorage atmStorage;
    private List<String> arguments;
    private static final String AVAILABLE_ARG = "AVAILABLE";

    DenominationsCommand(List<String> arguments) {
        this.arguments = arguments;
    }

    @Override
    public String run(AtmStorage atmStorage) throws Exception {
        this.atmStorage = atmStorage;
        return getDenominations().toString();
    }

    private List<Integer> getDenominations() throws Exception {
        try {
            if (arguments == null || arguments.size() == 0) {
                return atmStorage.getAllDenominations();
            }
            if (arguments.get(0).trim().equalsIgnoreCase(AVAILABLE_ARG)) {
                return atmStorage.getAvailableDenominations();
            }
        } catch (Exception ex) {
            throw new Exception("Invalid arguments");
        }
        throw new Exception("Invalid arguments");
    }
}
