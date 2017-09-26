package com.otus.hw15.data;

import lombok.Data;

@Data
public class UserVisualizerRequest {
    private String command;
    private long userId;
}
