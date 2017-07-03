package com.otus.hw05.sample.tests;

import com.otus.hw05.yactf.annotations.BeforeTest;
import com.otus.hw05.yactf.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Test
public class SimpleIncorrectTest {
    @Test
    void invalidTest() {
        assert 0 == 1;
    }
}
