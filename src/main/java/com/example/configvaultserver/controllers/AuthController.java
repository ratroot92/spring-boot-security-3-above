package com.example.configvaultserver.controllers;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.configvaultserver.dto.request.PostLoginRequest;
import com.example.configvaultserver.dto.request.PostRegisterUserRequest;
import com.example.configvaultserver.dto.response.PostLoginResponse;
import com.example.configvaultserver.dto.response.PostRegisterUserResponse;
import com.example.configvaultserver.helpers.ApiResponse;
import com.example.configvaultserver.service.TokenService;
import com.example.configvaultserver.service.AuthService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/v1/auth")

public class AuthController {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(AuthController.class);
    private final TokenService tokenService;
    private final AuthService authService;

    AuthController(TokenService tokenService,
            AuthService authService) {
        this.tokenService = tokenService;
        this.authService = authService;
    }

    @PostMapping("/token")
    public String getToken(Authentication authentication) {
        LOG.debug("Token requested for user : '{}'", authentication.getName());
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token granted {}", token);
        return "" + token;
    }

    @PostMapping("/register")
    public ApiResponse<PostRegisterUserResponse> register(
            @RequestBody PostRegisterUserRequest postRegisterUserRequest) {
        return authService.registerUser(postRegisterUserRequest);
    }

    @PostMapping("/login")
    public ApiResponse<PostLoginResponse> login(@RequestBody PostLoginRequest postLoginRequest) {
        return authService.login(postLoginRequest);
    }

}
