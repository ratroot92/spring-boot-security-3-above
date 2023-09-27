// package com.example.configvaultserver.controllers;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.security.core.Authentication;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.configvaultserver.services.TokenService;

// @RestController

// @RequestMapping("/api/v1/auth")
// public class AuthController {

// private static final Logger LOG =
// LoggerFactory.getLogger(AuthController.class);

// private final TokenService tokenService;

// AuthController(TokenService tokenService) {
// this.tokenService = tokenService;
// }

// @PostMapping("/token")
// public String token(Authentication authentication) {
// LOG.debug("Tpoken requested for user : '{}'", authentication.getName());
// String token = tokenService.generateToken(authentication);
// LOG.debug("Token Granted {}", token);
// return "";
// }

// }
