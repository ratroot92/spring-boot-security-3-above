package com.example.configvaultserver.dto.request;

import lombok.Data;

@Data
public class PostRegisterUserRequestDto {
    private String email;
    private String password;
    private String name;
    private String phone;

}
