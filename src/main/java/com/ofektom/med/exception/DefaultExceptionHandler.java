package com.ofektom.med.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class DefaultExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ValidationError handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        logger.warn("Validation error on {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getBindingResult().getFieldErrors());
        Map<String, String> errors = new HashMap<>();
        for (FieldError error: ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ValidationError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "validation error", errors);
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        logger.error("Access Denied Exception on {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage(), ex);
        return buildErrorResponse(request, HttpStatus.FORBIDDEN, "Access Denied: " + ex.getMessage());
    }

    @ExceptionHandler(IllegalRequestException.class)
    public ResponseEntity<ApiError> IllegalRequestException(
            IllegalRequestException e, HttpServletRequest request) {
        logger.error("Illegal Request Exception on {} {}: {}", request.getMethod(), request.getRequestURI(), e.getMessage(), e);
        return buildErrorResponse(request, HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> BadRequestException(
            BadRequestException e, HttpServletRequest request) {
        logger.warn("Bad Request Exception on {} {}: {}", request.getMethod(), request.getRequestURI(), e.getMessage());
        return buildErrorResponse(request, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> NotFoundException(
            NotFoundException e, HttpServletRequest request) {
        logger.warn("Not Found Exception on {} {}: {}", request.getMethod(), request.getRequestURI(), e.getMessage());
        return buildErrorResponse(request, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> ConflictException(
            ConflictException e, HttpServletRequest request) {
        logger.warn("Conflict Exception on {} {}: {}", request.getMethod(), request.getRequestURI(), e.getMessage());
        return buildErrorResponse(request, HttpStatus.CONFLICT, e.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception e, HttpServletRequest request) {
        logger.error("Unhandled exception on {} {}: {}", request.getMethod(), request.getRequestURI(), e.getMessage(), e);
        return buildErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + e.getMessage());
    }


    private ResponseEntity<ApiError> buildErrorResponse(
            HttpServletRequest request, HttpStatus status, String message) {
        return buildErrorResponse(request, status, message, null);

    }

    @ExceptionHandler(java.lang.ClassNotFoundException.class)
    public ResponseEntity<String> handleClassNotFound(
            java.lang.ClassNotFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

    }

    private ResponseEntity<ApiError> buildErrorResponse(
            HttpServletRequest request, HttpStatus status, String message, List<ValidationError> errors) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                message,
                status.value(),
                LocalDateTime.now(),
                errors
        );
        return new ResponseEntity<>(apiError, status);
    }
}
