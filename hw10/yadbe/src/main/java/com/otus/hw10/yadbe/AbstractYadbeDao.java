package com.otus.hw10.yadbe;


import lombok.Setter;

import java.util.logging.Logger;

public abstract class AbstractYadbeDao {
    @Setter
    protected Executor executor;
    protected Logger logger;

    public AbstractYadbeDao() {
        logger = Logger.getLogger(getLoggerID());
    }

    protected abstract String getLoggerID();
}
