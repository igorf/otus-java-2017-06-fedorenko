package com.otus.hw07.atm.commands.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public final class DenominationsResult extends BasicCommandResult {
    @Getter @Setter private List<Integer> denominations;
}
