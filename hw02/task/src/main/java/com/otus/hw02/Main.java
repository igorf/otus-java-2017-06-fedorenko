package com.otus.hw02;

import com.otus.hw02.memory.calculator.MemoryCalculator;
import com.otus.hw02.memory.checker.MemoryChecker;
import com.otus.hw02.memory.checker.MemoryCheckerSelector;
import com.otus.hw02.memory.checker.MemoryCheckerType;

public class Main {
    public static void main(String args[]) {


        MemoryChecker m = getChecker(args);
        MemoryCalculator c = new MemoryCalculator();

        System.out.println("Empty string size: " + checkSize(m, ""));
        System.out.println("Empty array size: " + checkSize(m, new int[0]));

        System.out.println("Empty string calculated size: " + c.calculate(
                () -> {return "";}
        ));
        System.out.println("String(1) calculated size: " + c.calculate(
                () -> {return "X";}
        ));
        System.out.println("String(2) calculated size: " + c.calculate(
                () -> {return "XX";}
        ));
        System.out.println("String(3) calculated size: " + c.calculate(
                () -> {return "XXX";}
        ));
        System.out.println("String(4) calculated size: " + c.calculate(
                () -> {return "XXXX";}
        ));
        System.out.println("String(5) calculated size: " + c.calculate(
                () -> {return "XXXXX";}
        ));
        for (int i = 0; i < 2000; i++) {
            int finalI = i;
            System.out.println("Array calculated (" + i + ") size: " + c.calculate(
                    () -> {return new int[finalI];}
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
