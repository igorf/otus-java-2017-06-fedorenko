package com.otus.hw08.yacs;

import com.otus.hw08.yacs.field.processor.FieldProcessor;
import com.otus.hw08.yacs.handler.HandlerFactory;

public class ObjectWalker {

    private HandlerFactory factory;

    ObjectWalker(HandlerFactory factory) {
        this.factory = factory;
    }

    public Object walk(Object object) {
        for (FieldProcessor p: factory.getProcessors()) {
            if (p.isObjectOk(object)) {
                return p.processField(object, this);
            }
        }
        return null;
    }
}
