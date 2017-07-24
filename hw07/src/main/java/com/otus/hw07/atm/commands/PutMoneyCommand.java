package com.otus.hw07.atm.commands;

import com.otus.hw07.atm.commands.result.BasicCommandResult;
import com.otus.hw07.atm.commands.result.CommandResult;
import com.otus.hw07.atm.parts.AtmStorage;
import com.otus.hw07.atm.util.AtmCommandArgumentsParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PutMoneyCommand implements AtmCommand {
    private List<String> arguments;

    public PutMoneyCommand(List<String> arguments) {
        this.arguments = arguments;
    }

    @Override
    public CommandResult run(AtmStorage atm) throws Exception {
        Map<Integer, Integer> arguments = collectArguments();

        BasicCommandResult result = new BasicCommandResult();
        for (int key: arguments.keySet()) {
            try {
                atm.putMoneyTo(key, arguments.get(key));
            } catch (Exception e) {
                atm.rollbackInput();
                throw e;
            }
        }
        atm.commitInput();
        result.setMessage("Cache in: OK");
        return result;
    }

    private Map<Integer, Integer> collectArguments() throws Exception {
        try {
            Map<Integer, Integer> result = new HashMap<>();
            for (String part: arguments) {
                result.putAll(AtmCommandArgumentsParser.parseBanknotesListArgs(part));
            }
            return result;
        } catch (Exception ex) {
            throw new Exception("Invalid arguments");
        }
    }
}
