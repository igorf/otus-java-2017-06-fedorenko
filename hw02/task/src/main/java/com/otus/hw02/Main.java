package com.otus.hw02;

import com.otus.hw02.memory.calculator.MemoryCalculator;
import com.otus.hw02.memory.checker.MemoryChecker;
import com.otus.hw02.memory.checker.MemoryCheckerSelector;
import com.otus.hw02.memory.checker.MemoryCheckerType;
import com.otus.hw02.mock.MySampleMock;

public class Main {
    public static void main(String args[]) {


        MemoryChecker m = getChecker(args);
        MemoryCalculator c = new MemoryCalculator();

        System.out.println("Values from memory checker:");
        System.out.println("Empty string size: " + checkSize(m, ""));
        System.out.println("Empty array size: " + checkSize(m, new int[0]));
        System.out.println("Empty object size: " + checkSize(m, new Object()));
        System.out.println("Empty sample mock size: " + checkSize(m, new MySampleMock()));
        System.out.println("------------------------------------------------------------");

        System.out.println("Values from custom size calculator");

        System.out.println("Empty constant string calculated size: " + c.calculate(
                () -> ""
        ));

        System.out.println("Empty string with 'new' operator calculated size: " + c.calculate(
                () -> new String(new char[0])
        ));

        System.out.println("Empty array size: " + c.calculate(
                () -> new int[0]
        ));

        System.out.println("Empty object size: " + c.calculate(
                Object::new
        ));

        System.out.println("Empty sample mock size: " + c.calculate(
                MySampleMock::new
        ));

        System.out.println("------------------------------------------------------------");
        System.out.println("Memory dynamics:");

        System.out.println("String(1) calculated size: " + c.calculate(
                () -> "X"
        ));
        System.out.println("String(2) calculated size: " + c.calculate(
                () -> "XX"
        ));
        System.out.println("String(3) calculated size: " + c.calculate(
                () -> "XXX"
        ));
        System.out.println("String(4) calculated size: " + c.calculate(
                () -> "XXXX"
        ));
        System.out.println("String(5) calculated size: " + c.calculate(
                () -> "XXXXX"
        ));
        for (int i = 1; i < 10; i++) {
            int finalI = i;
            System.out.println("int[" + i + "] size: " + c.calculate(
                    () -> new int[finalI]
            ));
        }
    }

    private static MemoryChecker getChecker(String[] params) {

        if (params.length > 0) {
            if (params[0].equalsIgnoreCase("-usejamm")) {
                return MemoryCheckerSelector.getMemoryChecker(MemoryCheckerType.JAMM);
            }
            if (params[0].equalsIgnoreCase("-uselocalagent")) {
                return MemoryCheckerSelector.getMemoryChecker(MemoryCheckerType.INSTRUMENTATION);
            }
        }
        return MemoryCheckerSelector.getDefault();
    }

    private static long checkSize(MemoryChecker m, Object object) {
        try {
           return m.getObjectSize(object);
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return -1;
    }
}
