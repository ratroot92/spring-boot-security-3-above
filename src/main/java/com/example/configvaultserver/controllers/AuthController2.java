// package com.example.configvaultserver.controllers;

// import org.slf4j.LoggerFactory;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import com.example.configvaultserver.dto.request.PostLoginReq;
// import com.example.configvaultserver.dto.request.RegisterUserAuth;
// import com.example.configvaultserver.helpers.ApiResponse;
// import com.example.configvaultserver.services.AuthService;

// import jakarta.servlet.http.HttpServletRequest;

// @RestController
// @RequestMapping("/api/v1/auth")
// public class AuthController2 {

// private static final org.slf4j.Logger Log =
// LoggerFactory.getLogger(AuthController2.class);
// private final AuthService authService;
// private final ApiResponse apiResponse;
// private final AuthenticationManager authenticationManager;
// private final PasswordEncoder passwordEncoder;

// AuthController2(AuthService authService, ApiResponse apiResponse,
// AuthenticationManager authenticationManager,
// PasswordEncoder passwordEncoder) {
// this.authService = authService;
// this.apiResponse = apiResponse;
// this.authenticationManager = authenticationManager;
// this.passwordEncoder = passwordEncoder;

// }

// @PostMapping("/register")
// public ResponseEntity<?> register(@RequestBody RegisterUserAuth
// registerUserAuth,
// HttpServletRequest request) {
// try {
// ApiResponse apiResponse = authService.register(registerUserAuth);
// return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
// } catch (Exception e) {
// this.apiResponse.internalServerError(e.getMessage());
// return
// ResponseEntity.status(this.apiResponse.getStatus()).body(this.apiResponse);
// }
// }

// @PostMapping("/login")
// public ResponseEntity<?> login(@RequestBody PostLoginReq postLoginReq,
// HttpServletRequest request) {
// try {
// ApiResponse apiResponse = authService.login(postLoginReq,
// request.getHeader("gRecaptchaToken"));
// return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
// } catch (Exception e) {
// this.apiResponse.internalServerError(e.getMessage());
// return
// ResponseEntity.status(this.apiResponse.getStatus()).body(this.apiResponse);
// }
// }

// }
