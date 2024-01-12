package com.example.configvaultserver.dto.response;

import java.util.List;

import com.example.configvaultserver.models.UserEntity;

public record GetAllUsersDto(List<UserEntity> users) {
} 

