package com.otus.hw.yadbe;

import com.otus.hw.domain.DataSet;
import com.otus.hw.yadbe.connection.ConnectionHelper;
import com.otus.hw.yadbe.handler.StatementHandler;
import com.otus.hw.yadbe.sql.SqlCreator;
import com.otus.hw.yadbe.sql.SqlMapper;
import com.otus.hw.yadbe.sql.StatementPreparationData;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        return executeSQL(sqlCreator.readSQL(id, clazz), (stm -> fillOne(clazz, stm.getResultSet())));
    }

    public <T extends DataSet> List<T> findWhere(String searchString, final Class<T> clazz) throws Exception {
        return executeSQL(sqlCreator.whereSQL(searchString, clazz), (stm -> fillEntities(clazz, stm.getResultSet())));
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

    private <T extends DataSet> T fillOne(Class<T> entityClass, ResultSet rs) throws Exception {
        List<T> results = fillEntities(entityClass, rs);
        if (results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    private <T extends DataSet> List<T> fillEntities(Class<T> entityClass, ResultSet rs) throws Exception {
        List<T> result = new ArrayList<>();
        while (rs.next()) {
            T entity = entityClass.newInstance();
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
            result.add(entity);
        }
        return result;
    }    
}
