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
    private List<String> arguments;

    GetMoneyCommand(List<String> arguments) {
        this.arguments = arguments;
    }

    @Override
    public CommandResult run(AtmStorage atm) throws Exception {
        GetMoneyResult result = new GetMoneyResult();
        int amount = amountNeeded();
        this.atm = atm;

        if (atm.getAmount() < amount) {
            throw new NoEnoughMoneyException();
        }

        for (List<Integer> variant: new AmountSplitter(atm.getAvailableDenominations()).split(amount)) {
            if (takeFromATM(variant)) {
                result.setBanknotes(variant);
                return result;
            }
        }

        throw new NoBanknotesException();
    }

    private int amountNeeded() throws Exception {
        try {
            return Integer.valueOf(arguments.get(0));
        } catch (Exception ex) {
            throw new Exception("Invalid arguments");
        }
    }

    private boolean takeFromATM(List<Integer> banknotes) {
        return atm.getMoneyBundle(DenominationGrouper.group(banknotes));
    }
}
