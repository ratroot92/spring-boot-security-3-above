package com.example.configvaultserver.helpers;

import lombok.Data;

@Data
public class ApiError {
    private String message;
    private Integer status;

    public ApiError() {

    }

    public ApiError(Integer status, String message) {
        this.message = message;
        this.status = status;

    }

    public ApiError BadRequest(String message) {
        this.message = message;
        this.status = 400;
        return this;

    }
}
