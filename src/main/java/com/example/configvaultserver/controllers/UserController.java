package com.example.configvaultserver.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.configvaultserver.dto.request.PostCreateUserReq;
import com.example.configvaultserver.helpers.ApiResponse;
import com.example.configvaultserver.models.User;
import com.example.configvaultserver.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    public UserService userService;
    public ApiResponse apiResponse;

    UserController(UserService userService, ApiResponse apiResponse) {
        this.userService = userService;
        this.apiResponse = apiResponse;

    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody PostCreateUserReq postCreateUserReq) {
        try {
            User newUser = userService.createUser(postCreateUserReq);
            return ResponseEntity.ok().body(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create the user: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        try {
            List<User> users = userService.getUsers();
            return ResponseEntity.ok().body(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create the user: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        try {
            User user = userService.getUserById(id);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("user", user);
            this.apiResponse.setData(responseData).setMessage("success").setStatus(200);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create the user: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}") // Corrected path variable syntax
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
        try {
            System.out.println(id);
            Boolean userDeleted = userService.deleteUser(id);
            if (userDeleted) {
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("id", id);
                this.apiResponse.setData(responseData).setMessage("success").setStatus(200);
                return ResponseEntity.ok(this.apiResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete the user: " + e.getMessage()); // Corrected error
                                                                                                     // message
        }
    }

}
