package com.example.configvaultserver.dto.request;

import lombok.Data;

@Data
public class RegisterUserAuth {

    private String name;
    private String email;
    private String password;
    private String phone;

}
