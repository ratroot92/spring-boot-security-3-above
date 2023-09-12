package com.example.configvaultserver.dto.request.response;

import lombok.Data;

@Data
public class DeleteUserByIdRes {
    private Boolean success;
    private String message;
    private String id;

}
