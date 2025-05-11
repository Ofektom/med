package com.ofektom.med.dto.response;

public record ApiResponse<T>(
        int status_code,
        String message,
        String access_token,
        T data
) {}
