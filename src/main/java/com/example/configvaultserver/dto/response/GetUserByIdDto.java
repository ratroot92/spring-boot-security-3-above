package com.example.configvaultserver.dto.response;

import com.example.configvaultserver.models.UserEntity;

public record GetUserByIdDto(UserEntity user) {
    
}
