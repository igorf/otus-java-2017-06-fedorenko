package com.otus.hw16.frontend.communication;

import com.otus.hw16.frontend.data.messages.ResponseMessage;

public interface MessageAction {
    void setResponse(ResponseMessage message);
    void run();
}
