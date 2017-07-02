package com.otus.hw05.yactf.processor;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.logging.Logger;

public class TestRunner {

    private static Logger logger = Logger.getLogger(TestRunner.class.getName());
    private String testingPackage;
    private TestAnnotationProcessor annotationProcessor = new TestAnnotationProcessor();

    public TestRunner(String testingPackage) {
        this.testingPackage = testingPackage;
    }

    public TestRunResult run() {
        Set<Method> testingMethods = annotationProcessor.findTestableElementsForPackage(testingPackage);
        TestRunResult result = new TestRunResult();

        int testNumber = 0;
        for (Method m: testingMethods) {
            testNumber ++;
            logger.info("Running test " + testNumber + " of " + testingMethods.size() + " .........................");
            result.addResult(runSimpleTest(m));
            logger.info("......................... DONE");
        }

        return result;
    }

    private SimpleTestResult runSimpleTest(Method method) {
        SimpleTestResult result = new SimpleTestResult();
        result.setMethod(method);
        result.setResult(true);

        try {
            TestMethodBundle bundle = prepare(method);
            if (bundle.getBefore() != null) {
                invokeMethod(bundle.getBefore());
            }
            invokeMethod(bundle.getTest());
            if (bundle.getAfter() != null) {
                invokeMethod(bundle.getAfter());
            }
        } catch (Exception ex) {
            result.setResult(false);
            result.setCause(ex);
        }

        logger.info(result.toString());
        return result;
    }

    private void invokeMethod(Method method) throws Exception {
        Class<?> clazz = method.getDeclaringClass();
        Object instance = clazz.newInstance();
        method.invoke(instance);
    }

    private TestMethodBundle prepare(Method testMethod) throws Exception {
        TestMethodBundle bundle = new TestMethodBundle();
        bundle.setTest(testMethod);
        bundle.setBefore(annotationProcessor.findBeforeTestMethod(testMethod.getDeclaringClass()));
        bundle.setAfter(annotationProcessor.findAfterTestMethod(testMethod.getDeclaringClass()));

        return bundle;
    }
}
