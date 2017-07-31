package com.otus.hw08.yacs.field.processor.json;

import com.otus.hw08.yacs.ObjectWalker;
import com.otus.hw08.yacs.field.processor.FieldProcessor;

public class NumericProcessor implements FieldProcessor {

    @Override
    public Object processField(Object object, ObjectWalker decomposer) {
        return object;
    }

    @Override
    public boolean isObjectOk(Object object) {
        return Number.class.isAssignableFrom(object.getClass());
    }
}
