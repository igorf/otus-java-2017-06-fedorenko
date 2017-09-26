package com.otus.hw15.data.specific;

import com.otus.hw15.data.model.User;

public interface UserFetcher {
    User find(long id);
}
