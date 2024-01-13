package com.example.configvaultserver.dto.entity;

import com.example.configvaultserver.enums.RoleEnum;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    private String id;
    private String name;
    private String email;
    private boolean isAccountVerified;
    private boolean status;
    private RoleEnum role;
    
}
