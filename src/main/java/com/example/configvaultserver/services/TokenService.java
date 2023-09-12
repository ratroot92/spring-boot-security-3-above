// package com.example.configvaultserver.services;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.oauth2.jwt.JwtClaimsSet;
// import org.springframework.security.oauth2.jwt.JwtEncoder;
// import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
// import org.springframework.stereotype.Service;

// import com.example.configvaultserver.helpers.ApiResponse;

// import java.time.Instant;
// import java.time.temporal.ChronoUnit;
// import java.util.Collections;
// import java.util.Map;
// import java.util.stream.Collectors;

// @Service
// public class TokenService {
// private final JwtEncoder encoder;
// private final ApiResponse apiResponse;

// public TokenService(JwtEncoder encoder, ApiResponse apiResponse) {
// this.encoder = encoder;
// this.apiResponse = apiResponse;

// }

// public ApiResponse generateToken(Authentication authentication) {
// Instant now = Instant.now();
// String scope = authentication.getAuthorities().stream()
// .map(GrantedAuthority::getAuthority)
// .collect(Collectors.joining(" "));
// JwtClaimsSet claims = JwtClaimsSet.builder()
// .issuer("self")
// .issuedAt(now)
// .expiresAt(now.plus(1, ChronoUnit.HOURS))
// .subject(authentication.getName())
// .claim("scope", scope)
// .build();
// String accessToken =
// this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
// Map<String, Object> responseData = Collections.singletonMap("accessToken",
// accessToken);
// return this.apiResponse.success("Login Success", responseData);
// // return
// // this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
// }

// }