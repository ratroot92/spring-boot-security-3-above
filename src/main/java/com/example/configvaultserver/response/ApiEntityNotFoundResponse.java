package com.example.configvaultserver.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiEntityNotFoundResponse {
    private String message;
    private String entity;
    private String exception;

    public ApiEntityNotFoundResponse(String message, String entity, String exception) {
        super();
        this.message = message;
        this.entity = entity;
        this.exception = exception;

    }
}
