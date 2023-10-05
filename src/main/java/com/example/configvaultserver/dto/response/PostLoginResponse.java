package com.example.configvaultserver.dto.response;

import org.springframework.security.core.Authentication;

public record PostLoginResponse(Authentication authentication, String accessToken) {

}
