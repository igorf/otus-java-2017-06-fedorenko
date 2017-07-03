package com.otus.hw05.sample.tests;

import com.otus.hw05.yactf.annotations.BeforeTest;
import com.otus.hw05.yactf.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Test
public class SimpleIncorrectTest {
    private List<Integer> victim = new ArrayList<>(3);

    @BeforeTest
    void setUp() {
        victim.add(1);
        victim.add(2);
        victim.add(3);
    }

    @Test
    void invalidTest() {
        assert victim.size() == 5;
    }
}
