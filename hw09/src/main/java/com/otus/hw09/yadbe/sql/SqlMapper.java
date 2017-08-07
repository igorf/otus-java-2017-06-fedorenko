package com.otus.hw09.yadbe.sql;

import com.otus.hw09.yadbe.DataSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class SqlMapper {

    public String getTableFieldByField(Field field) {
        Annotation[] annotations = field.getDeclaredAnnotations();
        for (Annotation a: annotations) {
            if (a.getClass() == Column.class) {
                Column column = field.getAnnotation(Column.class);
                String name = column.name();
                if (!name.isEmpty()) {
                    return name;
                }
            }
        }

        return field.getName();
    }

    public boolean isFieldTransient(Field field) {
        return field.getAnnotation(Transient.class) != null;
    }

    public String getTableNameByClass(Class<? extends DataSet> clazz) {
        Entity entityAnnotation = clazz.getAnnotation(Entity.class);
        if (entityAnnotation != null) {
            String name = entityAnnotation.name();
            if (!name.isEmpty()) {
                return name;
            }
        }

        return clazz.getSimpleName();
    }
}
