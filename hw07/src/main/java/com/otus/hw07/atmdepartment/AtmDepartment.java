package com.otus.hw07.atmdepartment;

import com.otus.hw07.atm.Atm;
import com.otus.hw07.atm.commands.RestoreCommand;
import com.otus.hw07.atm.commands.ShowAmountCommand;
import com.otus.hw07.atm.commands.result.ShowAmountResult;
import com.otus.hw07.atm.state.AtmInitialState;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class AtmDepartment {
    @Getter private Map<String, Atm> atmMap = new HashMap<>();

    public void createATM(String handle, AtmInitialState state) throws Exception {
        atmMap.put(handle, new Atm(state));
    }

    public long getTotalAmount() throws Exception {
        long amount = 0;
        for (Atm atm: atmMap.values()) {
            amount += ((ShowAmountResult)atm.runCommand(new ShowAmountCommand())).getAmount();
        }
        return amount;
    }

    public void restore() throws Exception {
        for (Atm atm: atmMap.values()) {
            atm.runCommand(new RestoreCommand());
        }
    }

    public Atm get(String handle) throws Exception {
        return atmMap.get(handle);
    }

    public int count() throws Exception {
        return atmMap.size();
    }
}
