package com.otus.hw07.atm.commands;

import com.otus.hw07.atm.commands.result.CommandResult;
import com.otus.hw07.atm.commands.result.GetMoneyResult;
import com.otus.hw07.atm.exceptions.NoBanknotesException;
import com.otus.hw07.atm.exceptions.NoEnoughMoneyException;
import com.otus.hw07.atm.parts.AtmStorage;
import com.otus.hw07.atm.util.AmountSplitter;
import com.otus.hw07.atm.util.DenominationGrouper;

import java.util.List;

public class GetMoneyCommand implements AtmCommand {
    private AtmStorage atm;
    private int amountAsked;

    public GetMoneyCommand(List<String> arguments) {
        this(Integer.valueOf(arguments.get(0)));
    }

    public GetMoneyCommand(int amount) {
        amountAsked = amount;
    }

    @Override
    public CommandResult run(AtmStorage atm) throws Exception {
        GetMoneyResult result = new GetMoneyResult();
        this.atm = atm;

        if (atm.getAmount() < amountAsked) {
            throw new NoEnoughMoneyException();
        }

        for (List<Integer> variant: new AmountSplitter(atm.getAvailableDenominations()).split(amountAsked)) {
            if (takeFromATM(variant)) {
                result.setBanknotes(variant);
                return result;
            }
        }

        throw new NoBanknotesException();
    }

    private boolean takeFromATM(List<Integer> banknotes) {
        return atm.getMoneyBundle(DenominationGrouper.group(banknotes));
    }
}
