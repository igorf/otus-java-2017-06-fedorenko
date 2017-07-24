package com.otus.hw07.atm.commands.result;

import lombok.Data;

@Data
public class BasicCommandResult implements CommandResult {
    protected String message;
}
