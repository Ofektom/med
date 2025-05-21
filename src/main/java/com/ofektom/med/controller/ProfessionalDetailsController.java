package com.ofektom.med.controller;

import com.ofektom.med.dto.request.ProfessionalDetailsDto;
import com.ofektom.med.service.ProfessionalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{userId}/professional-details")
public class ProfessionalDetailsController {
    private final ProfessionalDetailsService professionalDetailsService;

    @Autowired
    public ProfessionalDetailsController(ProfessionalDetailsService professionalDetailsService) {
        this.professionalDetailsService = professionalDetailsService;
    }

    @GetMapping
    public ResponseEntity<?> getProfessionalDetails(@PathVariable("userId") Long userId) {
        return professionalDetailsService.getProfessionalDetailsByUserId(userId);
    }

    @PutMapping
    public ResponseEntity<?> updateProfessionalDetails(@PathVariable("userId") Long userId, @RequestBody ProfessionalDetailsDto professionalDetailsDTO) {
        return professionalDetailsService.updateProfessionalDetails(userId, professionalDetailsDTO);
    }
}
