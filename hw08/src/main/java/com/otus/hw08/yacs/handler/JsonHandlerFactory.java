package com.otus.hw08.yacs.handler;

import com.otus.hw08.yacs.field.processor.FieldProcessor;
import com.otus.hw08.yacs.field.processor.json.*;
import com.otus.hw08.yacs.output.creator.JsonOutputCreator;
import com.otus.hw08.yacs.output.creator.OutputCreator;

import java.util.ArrayList;
import java.util.List;

public class JsonHandlerFactory implements HandlerFactory {
    private final static List<FieldProcessor> processors = new ArrayList<>();
    private final static JsonOutputCreator creator = new JsonOutputCreator();

    static {
        processors.add(new NullProcessor());
        processors.add(new NumericProcessor());
        processors.add(new StringProcessor());
        processors.add(new IterableProcessor());
        processors.add(new ArrayProcessor());
        processors.add(new BooleanProcessor());
        processors.add(new CommonObjectProcessor());
    }

    @Override
    public List<FieldProcessor> getProcessors() {
        return processors;
    }

    @Override
    public OutputCreator getOutputCreator() {
        return creator;
    }
}
