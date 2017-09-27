package com.otus.hw15.data;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String command;
    private String login;
    private String password;
}
