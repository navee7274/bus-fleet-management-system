package com.busfleetmanagement.system.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // =========================
    // 404 - Resource Not Found
    // =========================

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                404,
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    // =========================
    // 404 - Wrong Endpoint
    // =========================

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(
            NoResourceFoundException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                404,
                "Not Found",
                "Endpoint not found",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    // =========================
    // 400 - Bad Request
    // =========================

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                400,
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(response);
    }

    // =========================
    // 400 - DTO Validation
    // =========================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        error.getField() + ": " + error.getDefaultMessage()
                )
                .findFirst()
                .orElse("Invalid request data");

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                400,
                "Bad Request",
                message,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(response);
    }

    // =========================
    // 409 - Conflict
    // =========================

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(
            ConflictException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                409,
                "Conflict",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(response);
    }

    // =========================
    // 500 - Internal Server Error
    // =========================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                500,
                "Internal Server Error",
                "Something went wrong",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}