package com.ofektom.med.controller;

import com.ofektom.med.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard")
@CrossOrigin(origins = {"http://localhost:5173", "https://airway-ng.netlify.app"}, allowCredentials = "true")
public class DashboardController {

    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_FRONT_DESK', 'ROLE_PHARMACIST', 'ROLE_LAB_SCIENTIST', 'ROLE_ACCOUNTANT')")
    public ResponseEntity<?> getDashboardStats() {
        return dashboardService.getDashboardStats();
    }
}

