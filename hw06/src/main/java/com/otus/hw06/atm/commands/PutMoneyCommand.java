package com.otus.hw06.atm.commands;

import com.otus.hw06.atm.parts.AtmStorage;
import com.otus.hw06.atm.util.AtmCommandArgumentsParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PutMoneyCommand implements AtmCommand {
    private List<String> arguments;

    public PutMoneyCommand(List<String> arguments) {
        this.arguments = arguments;
    }

    @Override
    public String run(AtmStorage atm) throws Exception {
        Map<Integer, Integer> arguments = collectArguments();

        for (int key: arguments.keySet()) {
            try {
                atm.putMoneyTo(key, arguments.get(key));
            } catch (Exception e) {
                atm.rollbackInput();
                throw e;
            }
        }
        atm.commitInput();
        return "Cache in: OK";
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
