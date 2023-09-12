package com.example.configvaultserver.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.configvaultserver.dto.request.PostCreateUserReq;
import com.example.configvaultserver.models.User;
import com.example.configvaultserver.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(PostCreateUserReq postCreateUserReq) {

        if (userRepository.existsByEmail(postCreateUserReq.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Create a new User instance
        User newUser = new User(
                postCreateUserReq.getName(),
                postCreateUserReq.getEmail(),
                postCreateUserReq.getPassword(),
                postCreateUserReq.getPhone());

        // Save the user to the repository and handle any potential exceptions
        try {
            return userRepository.save(newUser);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to create a new user", e);
        }
    }

    public Boolean deleteUser(String userId) {
        try {
            boolean userExists = userRepository.existsById(userId);
            if (userExists) {
                userRepository.deleteById(userId);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create a new user", e);
        }
    }

    public List<User> getUsers() {
        try {
            return userRepository.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to create a new user", e);
        }
    }

    public User getUserById(String id) {
        try {
            Optional<User> user = userRepository.findById(id);
            return user.orElse(null);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to retrieve the user by ID", e);
        }
    }

}
