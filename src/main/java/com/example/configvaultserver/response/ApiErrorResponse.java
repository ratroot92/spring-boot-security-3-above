package com.example.configvaultserver.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorResponse {
    private String message;
    private String exception;
        private Object trace;


    public ApiErrorResponse(String message,String exception,Object trace) {
        super();
        this.message = message;
        this.exception = exception;
        this.trace = trace;

    }

}
