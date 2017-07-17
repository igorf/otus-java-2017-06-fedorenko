package com.otus.hw06.atm.parts;

public class AtmCurrencyCell {
    private int nominal = 0;
    private int quantity = 0;

    public AtmCurrencyCell(int nominal) throws Exception {
        if (nominal <= 0) {
            throw new Exception("Banknote nominal must be positive");
        }
        this.nominal = nominal;
    }

    public void add(int banknotes) throws Exception {
        if (banknotes < 0) {
            throw new Exception("Could not add negative number of banknotes");
        }
        quantity += banknotes;
    }

    public int amount() {
        return nominal * quantity;
    }

    public void take(int banknotes) throws Exception {
        if (banknotes < 0) {
            throw new Exception("Could not take negative number of banknotes");
        }
        if (banknotes > quantity) {
            throw new Exception("No enough money in the cell");
        }
        quantity -= banknotes;
    }

    public void clean() {
        this.quantity = 0;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getNominal() {
        return nominal;
    }
}
