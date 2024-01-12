package com.example.configvaultserver.service;

import java.util.concurrent.ExecutionException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.configvaultserver.dto.request.PostLoginRequestDto;
import com.example.configvaultserver.dto.request.PostRegisterUserRequestDto;
import com.example.configvaultserver.dto.response.PostLoginResponse;
import com.example.configvaultserver.dto.response.PostRegisterUserResponse;
import com.example.configvaultserver.helpers.ApiResponse;
import com.example.configvaultserver.models.UserEntity;
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
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                postLoginRequestDto.getEmail(),
                postLoginRequestDto.getPassword());
        authentication = authenticationProvider.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenService.generateToken(authentication);
        UserEntity user=userRepository.findByEmail(postLoginRequestDto.getEmail());
        log.info("******************************************");
        log.info("authentication =>"+authentication.toString());
        log.info("authentication =>"+authentication.isAuthenticated());
        log.info("******************************************");
        PostLoginResponse postLoginResponse = new PostLoginResponse(user, accessToken);
        return  postLoginResponse;
    }

    public PostRegisterUserResponse registerUser(PostRegisterUserRequestDto postRegisterUserRequest) throws Exception {
        UserEntity userExists = userRepository.findByEmail(postRegisterUserRequest.getEmail());
        if(userExists!=null){
            throw new Exception("Duplicate User");
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
        return  postRegisterUserResponse;

    }

    public void isAutheticated(){
         
    }

}
