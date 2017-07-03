package com.otus.hw05.yactf.processor;

import com.otus.hw05.yactf.annotations.AfterTest;
import com.otus.hw05.yactf.annotations.BeforeTest;
import com.otus.hw05.yactf.annotations.Test;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

class TestAnnotationProcessor {
    Set<Method> findTestableElementsForPackage(String pkg) {
        Reflections reflections = new Reflections(pkg, new MethodAnnotationsScanner());
        return reflections.getMethodsAnnotatedWith(Test.class);
    }

    private Method findAnnotatedMethodForClass(Class<?> clazz, final Class<? extends Annotation> annotation) {
        for (Method m: clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(annotation)) {
                return m;
            }
        }
        return null;
    }

    Method findBeforeTestMethod(Class<?> clazz) {
        return findAnnotatedMethodForClass(clazz, BeforeTest.class);
    }

    Method findAfterTestMethod(Class<?> clazz) {
        return findAnnotatedMethodForClass(clazz, AfterTest.class);
    }
}
