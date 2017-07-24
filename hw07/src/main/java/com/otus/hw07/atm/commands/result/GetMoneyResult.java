package com.otus.hw07.atm.commands.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public final class GetMoneyResult extends BasicCommandResult {
    @Getter @Setter private List<Integer> banknotes;

    public void setMessage(String message) {
        throw new IllegalArgumentException();
    }

    @Override
    public String getMessage() {
        if (banknotes == null || banknotes.size() == 0) {
            return "Empty set";
        }
        String result = "OK. Banknotes: ";
        for (int i = 0; i < banknotes.size(); i++) {
            result += banknotes.get(i);
            if (i < banknotes.size() - 1) {
                result += ", ";
            }
        }
        return result;
    }
}
