package com.otus.hw05.yactf.processor;

import java.util.HashSet;
import java.util.Set;

public class TestRunResult {
    private Set<SimpleTestResult> results = new HashSet<SimpleTestResult>();
    private boolean afterAll = true;

    void addResult(SimpleTestResult result) {
        if (!result.isResult()) {
            afterAll = false;
        }
        results.add(result);
    }

    public boolean isOk() {
        return afterAll;
    }
}
