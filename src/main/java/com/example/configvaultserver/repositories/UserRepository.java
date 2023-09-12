package com.example.configvaultserver.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.configvaultserver.models.User;

public interface UserRepository extends MongoRepository<User, String> {
    Boolean existsByEmail(String email);

    // Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

}
