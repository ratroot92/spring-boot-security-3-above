package com.example.configvaultserver.dto.response;

import org.springframework.security.core.Authentication;

import com.example.configvaultserver.models.UserEntity;

public record PostLoginResponse(UserEntity user, String accessToken) {

}
