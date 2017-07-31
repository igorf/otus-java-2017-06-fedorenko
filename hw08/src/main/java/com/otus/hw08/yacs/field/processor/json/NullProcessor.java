package com.otus.hw08.yacs.field.processor.json;

import com.otus.hw08.yacs.ObjectWalker;
import com.otus.hw08.yacs.field.processor.FieldProcessor;

public class NullProcessor implements FieldProcessor {

    @Override
    public Object processField(Object object, ObjectWalker decomposer) {
        return null;
    }

    @Override
    public boolean isObjectOk(Object object) {
        return object == null;
    }
}
