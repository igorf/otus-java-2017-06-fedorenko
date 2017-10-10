package com.otus.hw16.frontend.client;

import com.otus.hw16.common.config.ServerConfig;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ServerConfigWrapper {
    private String host;
    private int port;

    ServerConfig getServerConfig() {
        ServerConfig result = new ServerConfig();
        result.setHost(host);
        result.setPort(port);
        return result;
    }
}
