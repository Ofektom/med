package com.ofektom.med.exception;

import java.util.Map;

public record ValidationError(
        int status_code,
        String error,
        Map<String, String>detail
) {
}