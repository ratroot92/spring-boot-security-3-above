package com.example.configvaultserver.dto.request;

import lombok.Data;

@Data
public class PostLoginRequestDto {
    private String email;
    private String password;

}
