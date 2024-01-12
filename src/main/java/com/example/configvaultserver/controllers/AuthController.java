package com.example.configvaultserver.controllers;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.configvaultserver.dto.request.PostLoginRequestDto;
import com.example.configvaultserver.dto.request.PostRegisterUserRequestDto;
import com.example.configvaultserver.dto.response.PostLoginResponse;
import com.example.configvaultserver.dto.response.PostRegisterUserResponse;
import com.example.configvaultserver.exceptions.CustomException;
import com.example.configvaultserver.helpers.ApiResponse;
import com.example.configvaultserver.response.ApiErrorResponse;
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


  
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ApiResponse<PostRegisterUserResponse> register(
            @RequestBody PostRegisterUserRequestDto postRegisterUserRequest) throws Exception{
        return new ApiResponse<PostRegisterUserResponse>().created(authService.registerUser(postRegisterUserRequest),"User registered successfully.");
    }

    @PostMapping("/login")
    public ApiResponse<PostLoginResponse> login(@RequestBody PostLoginRequestDto postLoginRequestDto) {
       PostLoginResponse postLoginResponse= authService.login(postLoginRequestDto);
        return new ApiResponse<PostLoginResponse>().ok(postLoginResponse);
    }

    @GetMapping("/is-authenticated")
    public ApiResponse<String> isAuthenticated() {
        authService.isAutheticated();
        return new ApiResponse<String>().ok("User is authenticated.");
    }

}
