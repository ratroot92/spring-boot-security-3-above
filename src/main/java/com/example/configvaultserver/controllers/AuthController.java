package com.example.configvaultserver.controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.configvaultserver.service.TokenService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/auth")

public class AuthController {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(AuthController.class);
    private final TokenService tokenService;

    AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public String getToken(Authentication authentication) {

        LOG.debug("Token requested for user : '{}'", authentication.getName());
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token granted {}", token);
        return "" + token;
    }

}
