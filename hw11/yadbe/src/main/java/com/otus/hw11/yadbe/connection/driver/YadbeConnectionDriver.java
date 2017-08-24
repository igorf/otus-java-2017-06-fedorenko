package com.otus.hw11.yadbe.connection.driver;

import java.sql.Connection;
import java.util.Properties;

public interface YadbeConnectionDriver {
    Connection createConnection(Properties properties) throws Exception;
}
