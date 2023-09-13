package com.example.configvaultserver.helpers;

import lombok.Data;
import java.util.Map;

import org.springframework.stereotype.Component;

@Data
@Component
public class ApiResponse {
    String message;
    Integer status;
    Boolean success;
    Map<String, Object> data;

    public ApiResponse badRequest(String message) {
        this.success = false;
        this.status = 400;
        this.data = null;
        this.message = message;
        return this;

    }

    public ApiResponse internalServerError(String message) {
        this.success = true;
        this.status = 500;
        this.data = null;
        this.message = message;
        return this;

    }

    public ApiResponse success(String message, Map<String, Object> data) {
        this.success = true;
        this.status = 200;
        this.data = data;
        this.message = message;
        return this;

    }

    public ApiResponse unAuthroized(String message) {
        this.success = false;
        this.status = 401;
        this.data = null;
        this.message = message;
        return this;

    }

    public ApiResponse unAuthroized() {
        this.success = false;
        this.status = 401;
        this.data = null;
        this.message = "unAuthrozied";
        return this;

    }

    public ApiResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public ApiResponse setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public ApiResponse setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }

    public ApiResponse setSuccess() {
        this.success = true;
        return this;
    }

    public ApiResponse setFailure() {
        this.success = false;
        return this;
    }

}
