package com.otus.hw05.yactf.processor;

import lombok.Data;

import java.lang.reflect.Method;

@Data
class TestMethodBundle {
    private Method before;
    private Method test;
    private Method after;
}
