package com.otus.hw08.yacs.handler;

import com.otus.hw08.yacs.field.processor.FieldProcessor;
import com.otus.hw08.yacs.output.creator.OutputCreator;

import java.util.List;

public interface HandlerFactory {
    List<FieldProcessor> getProcessors();
    OutputCreator getOutputCreator();
}
