package com.example.configvaultserver.dto.request;

import com.example.configvaultserver.enums.RoleEnum;

import lombok.Data;

@Data
public class PostRegisterUserRequestDto {
    private String email;
    private String password;
    private String name;
    private String phone;
    private RoleEnum role;


}
