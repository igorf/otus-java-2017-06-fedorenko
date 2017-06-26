package com.otus.hw04.burn;

import org.junit.Test;

import static org.junit.Assert.*;

public class MemoryBurnerTest {
    @Test
    public void start() throws Exception {
        MemoryBurner burner = new MemoryBurner();
        burner.setObjectsPerRun(1000);
        burner.setTimeout(10000);
        burner.start();
        Thread.sleep(100);
        burner.stop();

        assert burner.length() == 500;
    }
}