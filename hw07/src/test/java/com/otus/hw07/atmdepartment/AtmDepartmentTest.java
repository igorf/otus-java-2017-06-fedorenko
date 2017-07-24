package com.otus.hw07.atmdepartment;

import com.otus.hw07.atm.commands.GetMoneyCommand;
import com.otus.hw07.atm.commands.ShowAmountCommand;
import com.otus.hw07.atm.commands.result.ShowAmountResult;
import com.otus.hw07.atm.state.AtmInitialState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AtmDepartmentTest {

    private AtmInitialState initialState1;
    private AtmInitialState initialState2;
    private AtmInitialState initialState3;
    private AtmDepartment department;

    @Before
    public void setUp() throws Exception {
        initialState1 = new AtmInitialState();
        initialState2 = new AtmInitialState();
        initialState3 = new AtmInitialState();

        initialState1.addCellState(1, 10);
        initialState1.addCellState(2, 10);
        initialState1.addCellState(5, 10);

        initialState2.addCellState(1, 20);
        initialState2.addCellState(2, 1);
        initialState2.addCellState(5, 1);

        initialState3.addCellState(1, 5);
        initialState3.addCellState(2, 10);
        initialState3.addCellState(5, 10);

        department = new AtmDepartment();
    }

    @Test
    public void createATM() throws Exception {
        department.createATM("atm1", initialState1);
        department.createATM("atm2", initialState2);
        department.createATM("atm3", initialState3);

        assertEquals(3, department.count());
        ShowAmountResult result = (ShowAmountResult) department.get("atm1").runCommand(new ShowAmountCommand());
        assertEquals(80, result.getAmount());
    }

    @Test
    public void getTotalAmount() throws Exception {
        department.createATM("atm1", initialState1);
        department.createATM("atm2", initialState2);
        department.createATM("atm3", initialState3);

        assertEquals(182, department.getTotalAmount());
    }

    @Test
    public void restore() throws Exception {
        department.createATM("atm1", initialState1);
        department.createATM("atm2", initialState2);
        department.createATM("atm3", initialState3);

        department.get("atm1").runCommand(new GetMoneyCommand(60));
        assertEquals(122, department.getTotalAmount());
        department.restore();
        assertEquals(182, department.getTotalAmount());
    }

}