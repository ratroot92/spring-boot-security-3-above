package com.example.configvaultserver.controllers;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.configvaultserver.helpers.ApiResponse;
import com.example.configvaultserver.models.UserEntity;
import com.example.configvaultserver.service.UserService;

import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/users")

public class UserController {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ApiResponse<UserEntity> getUserById(@PathVariable("id") String id) {
        return userService.getById(id);

    }

    @GetMapping()
    public ApiResponse<List<UserEntity>> getAllUsers() {
        return userService.get();
    }
}
