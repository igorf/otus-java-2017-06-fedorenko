package com.otus.hw15.data.specific;

import javax.websocket.Session;

public interface UserLoginCarrier {
    void onLogin(Session client, boolean result);
}
