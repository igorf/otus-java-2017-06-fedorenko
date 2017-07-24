package com.otus.hw07.atm.commands;

import com.otus.hw07.atm.exceptions.UnknownCommandException;
import com.otus.hw07.atm.util.AtmCommandArgumentsParser;

import java.util.List;

public class AtmCommandFactory {
    private final static String[] VALID_COMMANDS = {"PUT", "GET", "AMOUNT", "DENOMINATIONS"};

    public static AtmCommand createCommand(String incoming) throws Exception {
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
                case "DENOMINATIONS":
                    atmCommand = new DenominationsCommand(splittedCommand);
                    break;
            }
        }
        if (atmCommand == null) {
            throw new UnknownCommandException(incoming);
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
