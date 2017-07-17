package com.otus.hw06.atm.commands;

import com.otus.hw06.atm.util.AtmCommandArgumentsParser;

import java.util.List;

public class AtmCommandFactory {
    private final static String[] VALID_COMMANDS = {"PUT", "GET", "AMOUNT"};

    public static AtmCommand createCommand(String incoming) {
        List<String> splittedCommand = AtmCommandArgumentsParser.splitIncomingCommand(incoming);
        AtmCommand atmCommand = null;
        if (splittedCommand.size() > 0 && isValid(splittedCommand.get(0))) {
            String cmd = splittedCommand.remove(0);
            switch (cmd.trim().toUpperCase()) {
                case "PUT":
                    atmCommand = new PutMoneyCommand(splittedCommand);
                    break;
                case "AMOUNT":
                    atmCommand = new ShowAmountCommand();
                    break;
                case "GET":
                    atmCommand = new GetMoneyCommand(splittedCommand);
                    break;
            }
        }
        return atmCommand;
    }

    private static boolean isValid(String command) {
        for (String s: VALID_COMMANDS) {
            if (s.equalsIgnoreCase(command)) {
                return true;
            }
        }
        return false;
    }
}
