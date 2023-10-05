package com.example.configvaultserver.dto.request;

import lombok.Data;

@Data
public class PostLoginRequest {
    private String email;
    private String password;

}
