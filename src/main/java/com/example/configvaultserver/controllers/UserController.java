package com.example.configvaultserver.controllers;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.configvaultserver.dto.response.GetAllUsersDto;
import com.example.configvaultserver.dto.response.GetUserByIdDto;
import com.example.configvaultserver.helpers.ApiResponse;
import com.example.configvaultserver.service.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/v1/users")

public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ApiResponse<GetUserByIdDto> getUserById(@PathVariable("id") String id) {
        return new ApiResponse<GetUserByIdDto>().ok(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUserById(@PathVariable("id") String id) throws NotFoundException {
        userService.deleteUserById(id);
        return new ApiResponse<String>().ok("User deleted successfully");
    }

    @GetMapping()
    public ApiResponse<GetAllUsersDto> getAllUsers() {
        return new ApiResponse<GetAllUsersDto>().ok(userService.get());
    }
}
