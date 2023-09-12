package com.example.configvaultserver.dto.request;

import lombok.Data;

@Data
public class PostLoginReq {

    private String email;
    private String password;

}
