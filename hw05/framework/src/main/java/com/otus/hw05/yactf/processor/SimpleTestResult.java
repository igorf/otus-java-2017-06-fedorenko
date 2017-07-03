package com.otus.hw05.yactf.processor;

import lombok.Data;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

@Data
class SimpleTestResult {
    private Method method;
    private boolean result;
    private Throwable cause;

    String getStringRepresentation() {
        String representation = method.getDeclaringClass().getName() + "::" + method.getName();
        if (!result) {
            representation += "-> FAILED";
            representation += "\nCause: " + getExceptionReason(cause);
            representation += "\n" + exceptionToStringTrace(cause);
        } else {
            representation += "-> OK";
        }
        return representation;
    }

    private static String exceptionToStringTrace(Throwable ex) {
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }

    private String getExceptionReason(Throwable throwable) {
        if (throwable.getLocalizedMessage() != null) {
            return throwable.getLocalizedMessage();
        }
        return (throwable.getMessage() != null) ? throwable.getMessage() : throwable.getClass().getSimpleName();
    }
}
