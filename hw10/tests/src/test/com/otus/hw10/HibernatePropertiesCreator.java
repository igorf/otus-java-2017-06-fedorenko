package com.otus.hw10;

import java.util.Properties;

class HibernatePropertiesCreator {
    static Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/otushw");
        properties.setProperty("hibernate.connection.username", "otus");
        properties.setProperty("hibernate.connection.password", "otus");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "validate");

        return properties;
    }
}
