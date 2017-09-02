package com.otus.hw13.connector;

import com.otus.hw.yadbe.connection.ConnectionHelper;
import com.otus.hw.yadbe.connection.driver.YadbeMysql;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class DbConnector {

    private static final String CONNECTION_PROP_FILE = "database.properties";
    private static final Logger logger = Logger.getLogger(DbConnector.class.getName());

    public static void connect() {
        ConnectionHelper.connect(new YadbeMysql(), readConnectionProperties());
    }

    public static void disconnect() {
        ConnectionHelper.disconnect();
    }

    private static Properties readConnectionProperties() {
        ClassLoader classLoader = DbConnector.class.getClassLoader();
        File file = new File(classLoader.getResource(CONNECTION_PROP_FILE).getFile());
        Properties result = new Properties();

        try (InputStream propsStream = new FileInputStream(file)) {
            result.load(propsStream);
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }

        return result;
    }
}
