package com.example.configvaultserver.dto.response;

import java.util.List;

import com.example.configvaultserver.models.UserModel;

public record GetAllUsersDto(List<UserModel> users) {
} 

