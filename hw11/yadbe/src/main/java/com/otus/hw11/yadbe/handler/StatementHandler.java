package com.otus.hw11.yadbe.handler;

import java.sql.Statement;

@FunctionalInterface
public interface StatementHandler<T> {
    T apply(Statement statement) throws Exception;
}
