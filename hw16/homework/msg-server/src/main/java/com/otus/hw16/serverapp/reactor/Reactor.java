package com.otus.hw16.serverapp.reactor;

import com.otus.hw16.common.reactor.AbstractReactor;
import com.otus.hw16.common.reactor.MessageReactor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Reactor extends AbstractReactor {
    @Autowired private Set<MessageReactor> reactors;

    @Override
    protected Set<MessageReactor> getReactors() {
        return reactors;
    }
}
