package com.example.configvaultserver.dto.response;

import com.example.configvaultserver.models.UserEntity;

public record PostRegisterUserResponse(UserEntity user, String accessToken) {

}