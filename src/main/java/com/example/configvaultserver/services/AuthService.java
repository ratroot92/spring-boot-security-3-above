package com.example.configvaultserver.services;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.example.configvaultserver.dto.request.PostLoginReq;
import com.example.configvaultserver.helpers.ApiResponse;
import com.example.configvaultserver.models.RecaptchaResponse;
import com.example.configvaultserver.models.User;
import com.example.configvaultserver.repositories.UserRepository;

@Service
public class AuthService {

    @Value("${recaptcha.secretKey}")
    private String recaptchaSecretKey;

    private final UserRepository userRepository;
    private final ApiResponse apiResponse;
    private final RecaptchaService recaptchaService;

    public AuthService(UserRepository userRepository, ApiResponse apiResponse, RecaptchaService recaptchaService) {
        this.userRepository = userRepository;
        this.apiResponse = apiResponse;
        this.recaptchaService = recaptchaService;

    }

    public ApiResponse login(PostLoginReq postLoginReq, String recaptchaString) {
        try {
            if (recaptchaString.isBlank()) {
                return this.apiResponse.unAuthroized("Invalid Request.");
            }
            RecaptchaResponse recaptchaResponse = this.recaptchaService.validateToken(recaptchaString);

            if (recaptchaResponse.success() == false) {
                return this.apiResponse.unAuthroized("Invalid Request.");
            }
            if (recaptchaResponse.score() < 0.9) {
                return this.apiResponse.unAuthroized("Invalid Request.");

            }
            Optional<User> user = userRepository.findByEmail(postLoginReq.getEmail());
            if (user.isPresent()) {
                User newUser = user.get();
                if (newUser.getPassword().equals(postLoginReq.getPassword())) {
                    Map<String, Object> responseData = Collections.singletonMap("user", recaptchaResponse);

                    return this.apiResponse.success("Login Success", responseData);
                } else {
                    return this.apiResponse.unAuthroized("Invalid Password.");
                }
            } else {
                return this.apiResponse.unAuthroized("Invalid Credentials.");

            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
