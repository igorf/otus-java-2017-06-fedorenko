package com.otus.hw16.common.config;

import lombok.Data;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileInputStream;
import java.io.InputStream;

@Data
public class ServerConfig {
    private String host;
    private int port;

    public static ServerConfig[] readFromConfigFile(String configFile) {
        ServerConfig[] result = {};
        try (InputStream input = new FileInputStream(configFile)) {
            result = new ObjectMapper().readValue(input, ServerConfig[].class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }
}
