package com.ofektom.med.controller;

import com.ofektom.med.dto.request.EducationDto;
import com.ofektom.med.dto.request.ExperienceDto;
import com.ofektom.med.service.ProfessionalDetailsService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> getProfessionalDetails(@PathVariable Long userId) {
        return professionalDetailsService.getProfessionalDetailsByUserId(userId);
    }

    @PostMapping("/education")
    public ResponseEntity<?> addEducation(@PathVariable Long userId, @Valid @RequestBody EducationDto educationDto) {
        return professionalDetailsService.addEducation(userId, educationDto);
    }

    @PostMapping("/experience")
    public ResponseEntity<?> addExperience(@PathVariable Long userId, @Valid @RequestBody ExperienceDto experienceDto) {
        return professionalDetailsService.addExperience(userId, experienceDto);
    }

    @PostMapping("/conference")
    public ResponseEntity<?> addConference(@PathVariable Long userId, @RequestBody String conference) {
        return professionalDetailsService.addConference(userId, conference);
    }

    @PutMapping("/education/{educationId}")
    public ResponseEntity<?> updateEducation(@PathVariable Long userId, @PathVariable Long educationId, @Valid @RequestBody EducationDto educationDto) {
        return professionalDetailsService.updateEducation(userId, educationId, educationDto);
    }

    @PutMapping("/experience/{experienceId}")
    public ResponseEntity<?> updateExperience(@PathVariable Long userId, @PathVariable Long experienceId, @Valid @RequestBody ExperienceDto experienceDto) {
        return professionalDetailsService.updateExperience(userId, experienceId, experienceDto);
    }

    @PutMapping("/conference/{index}")
    public ResponseEntity<?> updateConference(@PathVariable Long userId, @PathVariable int index, @RequestBody String conference) {
        return professionalDetailsService.updateConference(userId, index, conference);
    }

    @DeleteMapping("/education/{educationId}")
    public ResponseEntity<?> deleteEducation(@PathVariable Long userId, @PathVariable Long educationId) {
        return professionalDetailsService.deleteEducation(userId, educationId);
    }

    @DeleteMapping("/experience/{experienceId}")
    public ResponseEntity<?> deleteExperience(@PathVariable Long userId, @PathVariable Long experienceId) {
        return professionalDetailsService.deleteExperience(userId, experienceId);
    }

    @DeleteMapping("/conference/{index}")
    public ResponseEntity<?> deleteConference(@PathVariable Long userId, @PathVariable int index) {
        return professionalDetailsService.deleteConference(userId, index);
    }
}