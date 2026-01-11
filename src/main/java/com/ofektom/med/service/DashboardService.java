package com.ofektom.med.service;

import com.ofektom.med.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface DashboardService {
    ResponseEntity<ApiResponse<?>> getDashboardStats();
}

