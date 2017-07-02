package com.otus.hw05.sample;

import com.otus.hw05.yactf.processor.TestRunResult;
import com.otus.hw05.yactf.processor.TestRunner;

import java.util.logging.Logger;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String... args) {
        TestRunResult result = new TestRunner("com.otus.hw05.sample.tests").run();
        logger.info("After all result: " + result.isOk());
    }
}
