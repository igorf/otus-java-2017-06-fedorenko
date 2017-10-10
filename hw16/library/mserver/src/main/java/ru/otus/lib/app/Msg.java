package ru.otus.lib.app;

import lombok.Getter;
/**
 * Created by tully.
 */
public abstract class Msg {
    public static final String CLASS_NAME_VARIABLE = "className";

    @Getter
    private final String className;

    protected Msg(Class<?> klass) {
        this.className = klass.getName();
    }
}
