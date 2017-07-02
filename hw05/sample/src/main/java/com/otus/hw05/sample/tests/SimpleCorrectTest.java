package com.otus.hw05.sample.tests;

import com.otus.hw05.yactf.annotations.BeforeTest;
import com.otus.hw05.yactf.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Test
public class SimpleCorrectTest {
    private List<Integer> victim = new ArrayList<>(5);

    @BeforeTest
    void setUp() {
        victim.add(0);
        victim.add(1);
        victim.add(2);
        victim.add(3);
        victim.add(4);
    }

    @Test
    void testOne() {
        assert victim.size() == 5;
        assert victim.get(1) == 1;
        victim.add(5);
        assert victim.size() == 6;
    }

    @Test
    void testTwo() {
        assert victim.size() == 5;
    }
}
