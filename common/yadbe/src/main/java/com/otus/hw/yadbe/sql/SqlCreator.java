package com.otus.hw.yadbe.sql;

import com.otus.hw.domain.DataSet;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlCreator {
    private final SqlMapper sqlMapper = new SqlMapper();

    public <T extends DataSet> StatementPreparationData createSQL(T entity) throws Exception {
        String tableName = sqlMapper.getTableNameByClass(entity.getClass());
        String sql = "insert into " + tableName + " (";
        Map<String, Object> data = entityToKV(entity);

        sql += StringUtils.join(data.keySet(), ", ");
        sql += ") values (";
        sql += StringUtils.repeat("?", ", ", data.values().size());
        sql += ")";

        return new StatementPreparationData(sql, data.values());
    }

    public <T extends DataSet> StatementPreparationData updateSQL(T entity) throws Exception {
        String tableName = sqlMapper.getTableNameByClass(entity.getClass());
        Map<String, Object> data = entityToKV(entity);
        List<String> elements = new ArrayList<>();
        List<Object> values = new ArrayList<>(data.values());
        values.add(entity.getId());

        for (String key: data.keySet()) {
            elements.add(key + " = ?");
        }

        String sql = "update " + tableName + " set ";
        sql += StringUtils.join(elements, ", ");
        sql += " where id = ?";

        return new StatementPreparationData(sql, values);
    }

    public <T extends DataSet> String readSQL(long id, Class<T> entityClass) throws Exception {
        String tableName = sqlMapper.getTableNameByClass(entityClass);
        return "select * from " + tableName + " where id = " + id;
    }

    public <T extends DataSet> String whereSQL(String searchString, Class<T> entityClass) throws Exception {
        String tableName = sqlMapper.getTableNameByClass(entityClass);
        return "select * from " + tableName + " where " + searchString;
    }

    public <T extends DataSet> String removeSQL(T entity) throws Exception {
        if (entity.getId() <= 0) {
            throw new Exception("Only persistent entities can be removed");
        }
        String tableName = sqlMapper.getTableNameByClass(entity.getClass());
        return "delete from " + tableName + " where id = " + entity.getId();
    }

    private <T extends DataSet> Map<String, Object> entityToKV(T entity) throws Exception {
        Map<String, Object> result = new HashMap<>();
        for (Field field: entity.getClass().getDeclaredFields()) {
            if (sqlMapper.isFieldTransient(field)) {
                continue;
            }
            result.put(sqlMapper.getTableFieldByField(field), fetchFieldValue(field, entity));
        }

        return result;
    }

    private Object fetchFieldValue(Field field, Object entity) throws Exception {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        Object result = field.get(entity);
        field.setAccessible(accessible);

        return result;
    }
}
