package com.otus.hw07.atm.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AmountSplitterTest {
    @Test
    public void split() throws Exception {
        List<Integer> denominations = new ArrayList<>();
        denominations.add(15);
        denominations.add(7);
        denominations.add(5);
        denominations.add(1);
        List result = new AmountSplitter(denominations).split(16);
        assert result.size() == 8;
    }
}