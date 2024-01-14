package com.example.configvaultserver.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.configvaultserver.exceptions.EntityNotFoundException;
import com.example.configvaultserver.response.ApiEntityNotFoundResponse;

@ControllerAdvice
public class NotFoundExceptionHandler {

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<ApiEntityNotFoundResponse> handler(EntityNotFoundException ex) {
        return new ResponseEntity<ApiEntityNotFoundResponse>(
                new ApiEntityNotFoundResponse(ex.getMessage(), ex.getEntity(), ex.getClass().getName()),
                HttpStatus.NOT_FOUND);
    }
}
