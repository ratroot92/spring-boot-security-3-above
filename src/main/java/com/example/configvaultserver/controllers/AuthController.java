package com.example.configvaultserver.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.configvaultserver.dto.request.PostLoginReq;
import com.example.configvaultserver.dto.request.RegisterUserRequestDto;
import com.example.configvaultserver.helpers.ApiResponse;
import com.example.configvaultserver.models.User;
import com.example.configvaultserver.services.AuthService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    AuthController(AuthService authService, ApiResponse apiResponse,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {
        this.authService = authService;

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequestDto RegisterUserRequestDto,
            HttpServletRequest request) {
        try {
            User user = authService.register(RegisterUserRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody PostLoginReq postLoginReq,
            HttpServletRequest request) {
        try {
            User user = authService.login(postLoginReq);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
