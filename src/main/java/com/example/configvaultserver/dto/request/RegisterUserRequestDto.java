package com.example.configvaultserver.dto.request;

import lombok.Data;

@Data
public class RegisterUserRequestDto {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private String phone;

}
