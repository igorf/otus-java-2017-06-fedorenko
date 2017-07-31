package com.otus.hw08.yacs;

import com.otus.hw08.yacs.handler.JsonHandlerFactory;

public class Yacs {
    public static String serializeToJSON(Object object) {
        JsonHandlerFactory hf = new JsonHandlerFactory();
        Object objectLayer = new ObjectWalker(hf).walk(object);
        return hf.getOutputCreator().composeOutput(objectLayer);
    }
}
