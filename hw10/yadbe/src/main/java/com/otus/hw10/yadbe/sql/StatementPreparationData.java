package com.otus.hw10.yadbe.sql;

import lombok.Data;

import java.util.Collection;

@Data
public class StatementPreparationData {
    private String sql;
    private Collection<Object> values;

    public StatementPreparationData(String sql, Collection<Object> values) {
        this.sql = sql;
        this.values = values;
    }
}
