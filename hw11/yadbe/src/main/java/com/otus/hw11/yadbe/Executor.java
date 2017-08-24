package com.otus.hw11.yadbe;

import com.otus.hw11.domain.DataSet;
import com.otus.hw11.yadbe.connection.ConnectionHelper;
import com.otus.hw11.yadbe.handler.StatementHandler;
import com.otus.hw11.yadbe.sql.SqlCreator;
import com.otus.hw11.yadbe.sql.SqlMapper;
import com.otus.hw11.yadbe.sql.StatementPreparationData;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;

public class Executor {
    private final SqlCreator sqlCreator = new SqlCreator();
    private final SqlMapper sqlMapper = new SqlMapper();

    private Connection getConnection() {
        return ConnectionHelper.get();
    }

    public <T extends DataSet> T save(T entity) throws Exception {
        if (entity.getId() <= 0) {
            return create(entity);
        } else {
            return update(entity);
        }
    }

    public <T extends DataSet> T load(long id, final Class<T> clazz) throws Exception {
        return executeSQL(sqlCreator.readSQL(id, clazz), (stm -> fillEntity(clazz, stm.getResultSet())));
    }

    public <T extends DataSet> void remove(T entity) throws Exception {
        executeSQL(sqlCreator.removeSQL(entity), (stm -> null));
    }

    private <T extends DataSet> T create(T entity) throws Exception {
        StatementPreparationData spd = sqlCreator.createSQL(entity);
        return executeParametrizedSQL(spd.getSql(), spd.getValues(), (stm -> {
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                entity.setId(id);
                return entity;
            } else {
                return null;
            }
        }));
    }

    private <T extends DataSet> T update(T entity) throws Exception {
        StatementPreparationData spd = sqlCreator.updateSQL(entity);
        return executeParametrizedSQL(spd.getSql(), spd.getValues(), (stm -> entity));
    }

    private <T> T executeSQL(String query, StatementHandler<T> handler) throws Exception {
        Connection connection = getConnection();
        if (connection == null) {
            throw new Exception("Executing statement on closed connection");
        }

        try(Statement statement = connection.createStatement()) {
            statement.execute(query);
            return handler.apply(statement);
        }
    }

    private <T> T executeParametrizedSQL(String query, Collection values, StatementHandler<T> handler) throws Exception {
        Connection connection = getConnection();
        if (connection == null) {
            throw new Exception("Executing statement on closed connection");
        }

        try(PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            if (values != null) {
                int index = 0;
                for (Object value: values) {
                    statement.setObject(++index, value);
                }
            }
            statement.execute();
            return handler.apply(statement);
        }
    }

    private <T extends DataSet> T fillEntity(Class<T> entityClass, ResultSet rs) throws Exception {
        T entity = entityClass.newInstance();

        if (!rs.next()) {
            return null;
        }

        for (Field field: entity.getClass().getDeclaredFields()) {
            if (sqlMapper.isFieldTransient(field)) {
                continue;
            }
            String column = sqlMapper.getTableFieldByField(field);
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            field.set(entity, rs.getObject(column, field.getType()));
            field.setAccessible(accessible);
        }

        return entity;
    }
}
