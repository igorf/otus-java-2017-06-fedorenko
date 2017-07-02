package com.otus.hw05.yactf.processor;

import lombok.Data;

import java.lang.reflect.Method;

@Data
public class SimpleTestResult {
    private Method method;
    private boolean result;
    private Throwable cause;

    @Override
    public String toString() {
        String name = method.getDeclaringClass().getName() + "::" + method.getName();
        String runResult = result ? " -> OK" : " FAILED [!]\n Cause: " + cause.getCause() + "\n" + cause.getStackTrace();

        return name + runResult;
    }
}
