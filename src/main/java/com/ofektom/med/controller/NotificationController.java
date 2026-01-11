package com.ofektom.med.controller;

import com.ofektom.med.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = {"http://localhost:5173", "https://airway-ng.netlify.app"}, allowCredentials = "true")
public class NotificationController {

    @GetMapping("/notifications")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_FRONT_DESK', 'ROLE_PHARMACIST', 'ROLE_LAB_SCIENTIST', 'ROLE_ACCOUNTANT')")
    public ResponseEntity<ApiResponse<?>> getNotifications() {
        // Stub endpoint - returns empty list for now
        // Can be implemented later with actual notification logic
        List<Map<String, Object>> notifications = new ArrayList<>();
        
        ApiResponse<List<Map<String, Object>>> response = new ApiResponse<>(
            HttpStatus.OK.value(),
            "Notifications retrieved successfully",
            null,
            notifications
        );
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/messages")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_FRONT_DESK', 'ROLE_PHARMACIST', 'ROLE_LAB_SCIENTIST', 'ROLE_ACCOUNTANT')")
    public ResponseEntity<ApiResponse<?>> getMessages() {
        // Stub endpoint - returns empty list for now
        // Can be implemented later with actual message logic
        List<Map<String, Object>> messages = new ArrayList<>();
        
        ApiResponse<List<Map<String, Object>>> response = new ApiResponse<>(
            HttpStatus.OK.value(),
            "Messages retrieved successfully",
            null,
            messages
        );
        
        return ResponseEntity.ok(response);
    }
}

