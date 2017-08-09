package com.otus.hw09.yadbe.handler;

import java.sql.Statement;

@FunctionalInterface
public interface StatementHandler<T> {
    T apply(Statement statement) throws Exception;
}
