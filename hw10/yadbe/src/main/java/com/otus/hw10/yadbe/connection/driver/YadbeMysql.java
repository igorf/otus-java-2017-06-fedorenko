package com.otus.hw10.yadbe.connection.driver;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class YadbeMysql implements YadbeConnectionDriver {
    private Properties properties;
    private final static int DEFAULT_PORT = 3306;
    private final static String DEFAULT_HOST = "localhost";

    @Override
    public Connection createConnection(Properties properties) throws Exception {
        this.properties = properties;
        Driver mysqlDriver = new Driver();
        DriverManager.registerDriver(mysqlDriver);
        return DriverManager.getConnection(getConnectionURL(), properties);
    }

    private String getConnectionURL() {
        String url = "";
        url += "jdbc:mysql://" + getHost() + ":" + getPort() + "/" + getDB();

        return url;
    }

    private int getPort() {
        return Integer.valueOf(properties.getProperty("port", String.valueOf(DEFAULT_PORT)));
    }

    private String getHost() {
        return properties.getProperty("host", DEFAULT_HOST);
    }

    private String getDB() {
        return properties.getProperty("db", "");
    }
}
