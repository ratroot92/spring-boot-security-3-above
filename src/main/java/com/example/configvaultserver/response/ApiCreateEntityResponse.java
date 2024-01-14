package com.example.configvaultserver.response;

public class ApiCreateEntityResponse<T> {
    T data;
    String message;

    public ApiCreateEntityResponse<T> created(String message) {
        this.message = message;
        return this;

    }

}
