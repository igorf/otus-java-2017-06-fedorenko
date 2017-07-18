package com.otus.hw06.atm.commands;

import com.otus.hw06.atm.exceptions.NoBanknotesException;
import com.otus.hw06.atm.exceptions.NoEnoughMoneyException;
import com.otus.hw06.atm.parts.AtmStorage;
import com.otus.hw06.atm.util.AmountSplitter;
import com.otus.hw06.atm.util.DenominationGrouper;

import java.util.List;

public class GetMoneyCommand implements AtmCommand {
    private AtmStorage atm;
    private List<String> arguments;

    GetMoneyCommand(List<String> arguments) {
        this.arguments = arguments;
    }

    @Override
    public String run(AtmStorage atm) throws Exception {
        int amount = amountNeeded();
        this.atm = atm;

        if (atm.getAmount() < amount) {
            throw new NoEnoughMoneyException();
        }

        for (List<Integer> variant: new AmountSplitter(atm.getAvailableDenominations()).split(amount)) {
            if (takeFromATM(variant)) {
                return showVariant(variant);
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

    private String showVariant(List<Integer> variant) {
        String result = "OK. Banknotes: ";
        for (int i = 0; i < variant.size(); i++) {
            result += variant.get(i);
            if (i < variant.size() - 1) {
                result += ", ";
            }
        }
        return result;
    }

    private boolean takeFromATM(List<Integer> banknotes) {
        return atm.getMoneyBundle(DenominationGrouper.group(banknotes));
    }
}
