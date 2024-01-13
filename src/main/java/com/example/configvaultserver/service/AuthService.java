package com.example.configvaultserver.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.configvaultserver.dto.entity.UserDto;
import com.example.configvaultserver.dto.request.PostLoginRequestDto;
import com.example.configvaultserver.dto.request.PostRegisterUserRequestDto;
import com.example.configvaultserver.dto.response.PostLoginResponse;
import com.example.configvaultserver.dto.response.PostRegisterUserResponse;
import com.example.configvaultserver.exceptions.CustomException;
import com.example.configvaultserver.exceptions.UnauthroizedException;
import com.example.configvaultserver.models.UserModel;
import com.example.configvaultserver.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    public PostLoginResponse login(PostLoginRequestDto postLoginRequestDto) {
        try {
            UserModel user = userRepository.findByEmail(postLoginRequestDto.getEmail());
            if (user == null) {
                throw new UnauthroizedException("Invalid Credentials");
            }
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    postLoginRequestDto.getEmail(),
                    postLoginRequestDto.getPassword());
            authentication = authenticationProvider.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = tokenService.generateToken(authentication);

            UserDto userDto = user.modelToDto(user);
            PostLoginResponse postLoginResponse = new PostLoginResponse(userDto, accessToken);
            return postLoginResponse;
        } catch (Exception ex) {
            throw new UnauthroizedException(ex.getMessage());
        }
    }

    public PostRegisterUserResponse registerUser(PostRegisterUserRequestDto postRegisterUserRequest) throws Exception {
        try {

            UserModel userExists = userRepository.findByEmail(postRegisterUserRequest.getEmail());
            if (userExists != null) {
                throw new CustomException("Duplicate User");
            }
            UserModel newUser = new UserModel();
            newUser.setEmail(postRegisterUserRequest.getEmail());
            newUser.setPassword(this.passwordEncoder.encode(postRegisterUserRequest.getPassword()));
            newUser.setName(postRegisterUserRequest.getName());
            newUser.setAccountVerified(true);
            newUser.setStatus(true);
            newUser.setRole(postRegisterUserRequest.getRole());
            newUser = userRepository.save(newUser);

            UserDto userDto = newUser.modelToDto(newUser);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    postRegisterUserRequest.getEmail(),
                    postRegisterUserRequest.getPassword());
            authentication = authenticationProvider.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = tokenService.generateToken(authentication);
            PostRegisterUserResponse postRegisterUserResponse = new PostRegisterUserResponse(userDto, accessToken);
            return postRegisterUserResponse;
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }

    }

    public UserDto isAutheticated() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.isAuthenticated() == false) {
            throw new UnauthroizedException("User not found.");

        }
        System.out.println("===========================");
        System.out.println("getPrincipal =>" + authentication.getPrincipal());
        System.out.println("getDetails =>" + authentication.getDetails());
        System.out.println("getAuthorities =>" + authentication.getAuthorities());
        System.out.println("getCredentials =>" + authentication.getCredentials());
        System.out.println("getName =>" + authentication.getName());
        System.out.println("isAuthenticated =>" + authentication.isAuthenticated());
        System.out.println("===========================");
        UserModel userModel = userRepository.findByEmail(authentication.getName());
        if (userModel == null) {
            log.warn("User not found for email: {}", authentication.getName());
            throw new UnauthroizedException("User not found.");
        }
        UserDto userDto = userModel.modelToDto(userModel);
        return userDto;

    }

}
