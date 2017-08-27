package com.otus.hw.yadbe.handler;

import java.sql.Statement;

@FunctionalInterface
public interface StatementHandler<T> {
    T apply(Statement statement) throws Exception;
}
