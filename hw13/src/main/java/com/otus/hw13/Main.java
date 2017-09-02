package com.otus.hw13;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String... args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("WEB-INF/applicationContext.xml");
    }
}
