package com.otus.hw16.frontend.data;

import lombok.Data;

@Data
public class UserVisualizerRequest {
    private String command;
    private long userId;
}
