package com.otus.hw07.atm.commands;

import com.otus.hw07.atm.commands.result.CommandResult;
import com.otus.hw07.atm.parts.AtmStorage;

public interface AtmCommand {
    CommandResult run(AtmStorage atm) throws Exception;
}
