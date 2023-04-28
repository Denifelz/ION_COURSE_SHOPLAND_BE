package com.db.repository;

import com.db.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmailAndPassword(String email, String password);
}
