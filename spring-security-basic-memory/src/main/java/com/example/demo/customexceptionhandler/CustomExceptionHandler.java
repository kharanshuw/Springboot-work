package com.example.demo.customexceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("Error occurred", e);
        return ResponseEntity.badRequest().body("Error occurred");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        log.error("Runtime error occurred", e);
        return ResponseEntity.badRequest().body("Runtime error occurred");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e) {
        log.error("Validation error occurred", e);
        return ResponseEntity.badRequest().body("Validation error occurred");
    }

}
