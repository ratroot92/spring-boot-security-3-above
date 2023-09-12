package com.example.configvaultserver.dto.request;

import lombok.Data;

@Data
public class PostRegisterReq {
    private String username;
    private String password;
    private String phone;
    private String email;
    private String role;

}
