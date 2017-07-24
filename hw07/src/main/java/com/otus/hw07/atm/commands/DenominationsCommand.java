package com.otus.hw07.atm.commands;

import com.otus.hw07.atm.commands.result.CommandResult;
import com.otus.hw07.atm.commands.result.DenominationsResult;
import com.otus.hw07.atm.parts.AtmStorage;

import java.util.List;

public class DenominationsCommand implements AtmCommand {
    private AtmStorage atmStorage;
    private static final String AVAILABLE_ARG = "AVAILABLE";
    private boolean onlyAvailable = false;

    public DenominationsCommand(String argument) {
        this(argument.equalsIgnoreCase(AVAILABLE_ARG));
    }

    public DenominationsCommand(List<String> arguments) {
        this(arguments.get(0));
    }

    public DenominationsCommand() {
        this("");
    }

    public DenominationsCommand(boolean availableOnly) {
        onlyAvailable = availableOnly;
    }

    @Override
    public CommandResult run(AtmStorage atmStorage) throws Exception {
        DenominationsResult result = new DenominationsResult();
        this.atmStorage = atmStorage;

        List<Integer> denominations = getDenominations();
        result.setMessage(denominations.toString());
        result.setDenominations(denominations);

        return result;
    }

    private List<Integer> getDenominations() throws Exception {
        try {
            return (onlyAvailable) ? atmStorage.getAvailableDenominations() : atmStorage.getAllDenominations();
        } catch (Exception ex) {
            throw new Exception("Invalid arguments");
        }
    }
}
