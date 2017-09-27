package com.otus.hw15.data.common;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractMessage {
    @Getter @Setter
    private MessageBroker messageBroker;
    public abstract void run(MessageAgent agent);
}
