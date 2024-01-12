package com.example.configvaultserver.helpers;

import lombok.Data;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@Component
public class ApiResponse<T> {
    private String message = "";
    private T data = null;

    public ApiResponse<T> created(T data, String message) {
        this.data = data;
        this.message = message;
        return this;
    }

    public ApiResponse<T> badRequest(String message) {
        return this;

    }

    public ApiResponse<T> ok(T data) {
        this.data = data;
        return this;
    }

    public ApiResponse<T> notFound(String message) {
        return this;
    }

    public ApiResponse<T> internalServerError(String message) {
        this.data = null;
        return this;

    }

    public ApiResponse<T> unAuthroized(String message) {
        this.data = null;
        return this;

    }

    public ApiResponse<T> unAuthroized() {
        this.data = null;
        return this;

    }

    public ApiResponse<T> setMessage(String message) {
        return this;
    }

    public ApiResponse<T> setStatus(HttpStatus status) {
        return this;
    }

    public ApiResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public ApiResponse<T> setSuccess() {
        return this;
    }

    public ApiResponse<T> setFailure() {
        return this;
    }

}