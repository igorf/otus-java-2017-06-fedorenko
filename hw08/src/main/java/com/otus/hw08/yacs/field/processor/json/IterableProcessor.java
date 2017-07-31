package com.otus.hw08.yacs.field.processor.json;

import com.otus.hw08.yacs.ObjectWalker;
import com.otus.hw08.yacs.field.processor.FieldProcessor;
import org.json.simple.JSONArray;

public class IterableProcessor implements FieldProcessor {

    @Override
    public Object processField(Object object, ObjectWalker decomposer) {
        JSONArray array = new JSONArray();
        for (Object o : ((Iterable) object)) {
            array.add(decomposer.walk(o));
        }
        return array;
    }

    @Override
    public boolean isObjectOk(Object object) {
        return object instanceof Iterable;
    }
}
