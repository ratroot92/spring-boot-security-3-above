package com.example.configvaultserver.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.example.configvaultserver.controllers.UserController;
import com.example.configvaultserver.helpers.ApiResponse;
import com.example.configvaultserver.models.UserEntity;
import com.example.configvaultserver.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(UserController.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public ApiResponse<List<UserEntity>> get() {
        List<UserEntity> users = userRepository.findAll();
        return new ApiResponse<List<UserEntity>>().ok(users);
    }

    public ApiResponse<UserEntity> getById(String id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return new ApiResponse<UserEntity>().notFound("User not found.");
        } else {
            return new ApiResponse<UserEntity>().ok(user);

        }
    }

}
