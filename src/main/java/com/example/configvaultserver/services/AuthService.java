package com.example.configvaultserver.services;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.configvaultserver.dto.request.PostLoginReq;
import com.example.configvaultserver.dto.request.RegisterUserRequestDto;
import com.example.configvaultserver.helpers.ApiResponse;
import com.example.configvaultserver.models.User;
import com.example.configvaultserver.repositories.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, ApiResponse apiResponse,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public User register(RegisterUserRequestDto RegisterUserRequestDto) {
        try {
            if (this.userRepository.existsByEmail(RegisterUserRequestDto.getEmail())) {
                try {
                    throw new Exception("User already exists.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String hashedPassword = this.passwordEncoder.encode(RegisterUserRequestDto.getPassword());
            User newUser = new User(
                    RegisterUserRequestDto.getName(),
                    RegisterUserRequestDto.getEmail(),
                    hashedPassword,
                    RegisterUserRequestDto.getPhone());
            newUser = userRepository.save(newUser);
            return newUser;

        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public User login(PostLoginReq postLoginReq) {
        try {
            Optional<User> user = userRepository.findByEmail(postLoginReq.getEmail());
            if (user.isPresent()) {
                User newUser = user.get();
                if (newUser.getPassword().equals(postLoginReq.getPassword())) {
                    return newUser;
                } else {
                    throw new RuntimeException("User not found.");
                }
            } else {
                throw new RuntimeException("User not found."); // Add this line
            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
