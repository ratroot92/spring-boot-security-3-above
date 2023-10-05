package com.example.configvaultserver.helpers;

import lombok.Data;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@Component
public class ApiResponse<T> {
    private int statusCode;
    private String message = "";
    private HttpStatus status;
    private boolean success;
    private T data = null;

    public ApiResponse<T> created(T data) {
        this.success = true;
        this.status = HttpStatus.CREATED;
        this.data = data;
        this.message = "SUCCESS";
        this.statusCode = 201;
        return this;
    }

    public ApiResponse<T> badRequest(String message) {
        this.success = false;
        this.status = HttpStatus.BAD_REQUEST;
        this.message = "FAILURE";
        this.statusCode = 400;
        return this;

    }

    public ApiResponse<T> ok(T data) {
        this.success = true;
        this.status = HttpStatus.OK;
        this.message = "SUCCESS";
        this.data = data;
        this.statusCode = 200;
        return this;
    }

    public ApiResponse<T> notFound(String message) {
        this.success = false;
        this.status = HttpStatus.NOT_FOUND;
        this.message = message;
        this.statusCode = 404;
        return this;
    }

    public ApiResponse<T> internalServerError(String message) {
        this.success = true;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.data = null;
        this.message = message;
        return this;

    }

    public ApiResponse<T> unAuthroized(String message) {
        this.success = false;
        this.status = HttpStatus.UNAUTHORIZED;
        this.data = null;
        this.message = message;
        return this;

    }

    public ApiResponse<T> unAuthroized() {
        this.success = false;
        this.status = HttpStatus.UNAUTHORIZED;
        this.data = null;
        this.message = "unAuthrozied";
        return this;

    }

    public ApiResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public ApiResponse<T> setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ApiResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public ApiResponse<T> setSuccess() {
        this.success = true;
        return this;
    }

    public ApiResponse<T> setFailure() {
        this.success = false;
        return this;
    }

}