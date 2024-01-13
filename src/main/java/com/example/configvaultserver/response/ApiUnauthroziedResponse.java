package com.example.configvaultserver.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiUnauthroziedResponse {
    private String message;
    private String exception;

    public ApiUnauthroziedResponse(String message, String exception) {
        super();
        this.message = message;
        this.exception = exception;

    }

}
