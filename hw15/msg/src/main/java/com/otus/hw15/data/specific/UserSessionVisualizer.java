package com.otus.hw15.data.specific;

import com.otus.hw15.data.model.User;

import javax.websocket.Session;

public interface UserSessionVisualizer {
    void showUser(Session session, User user);
}
