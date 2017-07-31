package com.otus.hw08.yacs.field.processor.json;

import com.otus.hw08.yacs.ObjectWalker;
import com.otus.hw08.yacs.field.processor.FieldProcessor;
import org.json.simple.JSONArray;

import java.lang.reflect.Array;

public class ArrayProcessor implements FieldProcessor {

    @Override
    public Object processField(Object object, ObjectWalker decomposer) {
        JSONArray array = new JSONArray();
        for (int i = 0; i < Array.getLength(object); i++) {
            array.add(decomposer.walk(Array.get(object, i)));
        }
        return array;
    }

    @Override
    public boolean isObjectOk(Object object) {
        return object.getClass().isArray();
    }
}
