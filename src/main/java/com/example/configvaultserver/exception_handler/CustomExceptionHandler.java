package com.example.configvaultserver.exception_handler;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.configvaultserver.exceptions.CustomException;
import com.example.configvaultserver.response.ApiErrorResponse;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({ CustomException.class })
    public ResponseEntity<ApiErrorResponse> customExceptionHandler(CustomException ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String sStackTrace = sw.toString();
        return new ResponseEntity<ApiErrorResponse>(
                new ApiErrorResponse(ex.getMessage(), ex.getClass().getName(), sStackTrace), HttpStatus.NOT_FOUND);
    }
}
