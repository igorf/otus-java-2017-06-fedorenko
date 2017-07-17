package com.otus.hw06.atm.commands;

import com.otus.hw06.atm.parts.AtmStorage;

public interface AtmCommand {
    String run(AtmStorage atm) throws Exception;
}
