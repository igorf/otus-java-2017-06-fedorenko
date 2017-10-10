package com.otus.hw16.db.repository;

import com.otus.hw16.frontend.data.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findOneByName(String name);
}
