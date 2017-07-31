package com.otus.hw08.yacs.output.creator;

import org.json.simple.JSONAware;

public class JsonOutputCreator implements OutputCreator {

    @Override
    public String composeOutput(Object objectLayer) {
        if (objectLayer instanceof JSONAware) {
            return ((JSONAware) objectLayer).toJSONString();
        }

        return "ERROR: object layer must be instance of JSONAware";
    }
}
