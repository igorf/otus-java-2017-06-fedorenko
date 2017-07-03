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
            logger.info("......................... DONE\n");
        }

        return result;
    }

    private SimpleTestResult runSimpleTest(Method method) {
        SimpleTestResult result = new SimpleTestResult();
        result.setMethod(method);
        result.setResult(true);

        try {
            Object instance = getClassInstanceForMethod(method);
            TestMethodBundle bundle = prepare(method);
            if (bundle.getBefore() != null) {
                invokeMethod(bundle.getBefore(), instance);
            }
            invokeMethod(bundle.getTest(), instance);
            if (bundle.getAfter() != null) {
                invokeMethod(bundle.getAfter(), instance);
            }
        } catch (java.lang.reflect.InvocationTargetException iex) {
            result.setCause(iex.getTargetException());
            result.setResult(false);
        } catch (Exception ex) {
            result.setResult(false);
            result.setCause(ex);
        }

        logger.info(result.getStringRepresentation());
        return result;
    }

    private Object getClassInstanceForMethod(Method method) throws Exception {
        ClassLoader loader = method.getDeclaringClass().getClassLoader();
        loader.setDefaultAssertionStatus(true);
        Class<?> clazz = Class.forName(method.getDeclaringClass().getName(), true, loader);

        return clazz.newInstance();
    }

    private void invokeMethod(Method method, Object instance) throws Exception {
        method.setAccessible(true);
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
