package com.example.configvaultserver.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.configvaultserver.exceptions.UnauthroizedException;
import com.example.configvaultserver.response.ApiUnauthroziedResponse;

@ControllerAdvice
public class UnauthroizedExceptionHandler {

    @ExceptionHandler({ UnauthroizedException.class })
    public ResponseEntity<ApiUnauthroziedResponse> customExceptionHandler(UnauthroizedException ex) {
        return new ResponseEntity<ApiUnauthroziedResponse>(
                new ApiUnauthroziedResponse(ex.getMessage(), ex.getClass().getName()), HttpStatus.UNAUTHORIZED);
    }
}
