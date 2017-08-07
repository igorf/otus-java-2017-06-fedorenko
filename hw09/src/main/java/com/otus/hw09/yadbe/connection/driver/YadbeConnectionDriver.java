package com.otus.hw09.yadbe.connection.driver;

import java.sql.Connection;
import java.util.Properties;

public interface YadbeConnectionDriver {
    Connection createConnection(Properties properties) throws Exception;
}
