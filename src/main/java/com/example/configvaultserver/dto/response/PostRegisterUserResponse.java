package com.example.configvaultserver.dto.response;

import com.example.configvaultserver.dto.entity.UserDto;

public record PostRegisterUserResponse(UserDto user, String accessToken) {

}
