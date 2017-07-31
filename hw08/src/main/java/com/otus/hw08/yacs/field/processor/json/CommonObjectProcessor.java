package com.otus.hw08.yacs.field.processor.json;

import com.otus.hw08.yacs.ObjectWalker;
import com.otus.hw08.yacs.field.processor.FieldProcessor;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.util.logging.Logger;

public class CommonObjectProcessor implements FieldProcessor {

    private final static Logger log = Logger.getLogger(CommonObjectProcessor.class.getName());

    @Override
    public Object processField(Object object, ObjectWalker decomposer) {
        JSONObject jsonObject = new JSONObject();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field: fields) {
            boolean accessible = field.isAccessible();
            try {
                field.setAccessible(true);
                jsonObject.put(field.getName(), decomposer.walk(field.get(object)));
            } catch (Exception ex) {
                log.warning(ex.getMessage());
            } finally {
                field.setAccessible(accessible);
            }
        }
        return jsonObject;
    }

    @Override
    public boolean isObjectOk(Object object) {
        return true;
    }
}
