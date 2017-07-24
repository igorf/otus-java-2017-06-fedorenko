package com.otus.hw07.atm.commands;

import com.otus.hw07.atm.commands.result.BasicCommandResult;
import com.otus.hw07.atm.commands.result.CommandResult;
import com.otus.hw07.atm.parts.AtmStorage;
import com.otus.hw07.atm.util.AtmCommandArgumentsParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PutMoneyCommand implements AtmCommand {
    private Map<Integer, Integer> incoming;
    private static Logger logger = Logger.getLogger(PutMoneyCommand.class.getName());

    public PutMoneyCommand(Map<Integer, Integer> arguments) {
        incoming = arguments;
    }

    public PutMoneyCommand(List<String> arguments) {
        this(collectArguments(arguments));
    }

    @Override
    public CommandResult run(AtmStorage atm) throws Exception {
        BasicCommandResult result = new BasicCommandResult();
        for (int key: incoming.keySet()) {
            try {
                atm.putMoneyTo(key, incoming.get(key));
            } catch (Exception e) {
                atm.rollbackInput();
                throw e;
            }
        }
        atm.commitInput();
        result.setMessage("Cache in: OK");
        return result;
    }

    private static Map<Integer, Integer> collectArguments(List<String> arguments) {
        Map<Integer, Integer> result = new HashMap<>();
        try {
            for (String part: arguments) {
                result.putAll(AtmCommandArgumentsParser.parseBanknotesListArgs(part));
            }
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }
        return result;
    }
}
