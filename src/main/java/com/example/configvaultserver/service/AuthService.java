package com.example.configvaultserver.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.configvaultserver.dto.request.PostLoginRequest;
import com.example.configvaultserver.dto.request.PostRegisterUserRequest;
import com.example.configvaultserver.dto.response.PostLoginResponse;
import com.example.configvaultserver.dto.response.PostRegisterUserResponse;
import com.example.configvaultserver.helpers.ApiResponse;
import com.example.configvaultserver.models.UserEntity;
import com.example.configvaultserver.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            AuthenticationProvider authenticationProvider, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
        this.tokenService = tokenService;

    }

    public ApiResponse<PostLoginResponse> login(PostLoginRequest postLoginRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                postLoginRequest.getEmail(),
                postLoginRequest.getPassword());
        authentication = authenticationProvider.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenService.generateToken(authentication);
        PostLoginResponse postLoginResponse = new PostLoginResponse(authentication, accessToken);
        return new ApiResponse<PostLoginResponse>().ok(postLoginResponse);
    }

    public ApiResponse<PostRegisterUserResponse> registerUser(PostRegisterUserRequest postRegisterUserRequest) {
        Boolean userExists = userRepository.existsByEmail(postRegisterUserRequest.getEmail());
        if (userExists.equals(true)) {
            return new ApiResponse<PostRegisterUserResponse>().badRequest("User already exists.");

        }
        UserEntity newUser = new UserEntity();
        newUser.setEmail(postRegisterUserRequest.getEmail());
        newUser.setPassword(this.passwordEncoder.encode(postRegisterUserRequest.getPassword()));
        newUser.setName(postRegisterUserRequest.getName());
        newUser.setIsAccountVerified(true);
        newUser.setStatus(true);
        newUser = userRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                postRegisterUserRequest.getEmail(),
                postRegisterUserRequest.getPassword());
        authentication = authenticationProvider.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenService.generateToken(authentication);
        PostRegisterUserResponse postRegisterUserResponse = new PostRegisterUserResponse(newUser, accessToken);
        return new ApiResponse<PostRegisterUserResponse>().ok(postRegisterUserResponse);

    }

}
