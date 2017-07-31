package com.otus.hw08.yacs.field.processor;

import com.otus.hw08.yacs.ObjectWalker;

public interface FieldProcessor {
    Object processField(Object field, ObjectWalker decomposer);
    boolean isObjectOk(Object object);
}
