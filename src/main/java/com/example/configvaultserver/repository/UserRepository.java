package com.example.configvaultserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.configvaultserver.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {
    UserModel findByEmail(String email);

    Boolean existsByEmail(String email);
}
