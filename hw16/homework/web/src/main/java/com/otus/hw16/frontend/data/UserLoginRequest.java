package com.otus.hw16.frontend.data;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String command;
    private String login;
    private String password;
}
