package com.felipe.musiclibraryback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException ex){
        logger.warning("Handled HTTP Exception: " + ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
    }
}
