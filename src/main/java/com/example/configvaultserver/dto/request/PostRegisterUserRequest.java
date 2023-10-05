package com.example.configvaultserver.dto.request;

import lombok.Data;

@Data
public class PostRegisterUserRequest {
    private String email;
    private String password;
    private String name;
    private String phone;

}
