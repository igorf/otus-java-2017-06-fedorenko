package com.otus.hw09.yadbe.connection;

import com.otus.hw09.yadbe.connection.driver.*;

import java.sql.Connection;
import java.util.Properties;
import java.util.logging.Logger;

public class ConnectionHelper {
    private static Logger logger = Logger.getLogger(ConnectionHelper.class.getName());
    private static Connection connection = null;

    public static Connection connect(YadbeConnectionDriver driver, Properties connectionProperties) {
        try {
            connection = driver.createConnection(connectionProperties);
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }
        return connection;
    }

    public static void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }
        connection = null;
    }

    public static Connection get() {
        return connection;
    }
}
