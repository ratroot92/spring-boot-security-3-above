package com.example.configvaultserver.dto.response;

import org.springframework.security.core.Authentication;

import com.example.configvaultserver.dto.entity.UserDto;
import com.example.configvaultserver.models.UserModel;

public record PostLoginResponse(UserDto user, String accessToken) {

}
