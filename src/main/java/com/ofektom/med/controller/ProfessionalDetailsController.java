package com.ofektom.med.controller;

import com.ofektom.med.dto.request.EducationDto;
import com.ofektom.med.dto.request.ExperienceDto;
import com.ofektom.med.dto.request.ConferenceDto;
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

    // Add new data (education, experience, or conference)
    @PostMapping("/{type}")
    public ResponseEntity<?> addProfessionalDetail(
            @PathVariable("userId") Long userId,
            @PathVariable("type") String type,
            @RequestBody Object detail) {
        return switch (type.toLowerCase()) {
            case "education" -> professionalDetailsService.addEducation(userId, (EducationDto) detail);
            case "experience" -> professionalDetailsService.addExperience(userId, (ExperienceDto) detail);
            case "conference" -> professionalDetailsService.addConference(userId, ((ConferenceDto) detail).getConference());
            default -> ResponseEntity.badRequest().body("Invalid type: " + type);
        };
    }

    // Update existing data (education, experience, or conference)
    @PutMapping("/{type}/{id}")
    public ResponseEntity<?> updateProfessionalDetail(
            @PathVariable("userId") Long userId,
            @PathVariable("type") String type,
            @PathVariable("id") Long id, // For conferences, this will be the index
            @RequestBody Object detail) {
        return switch (type.toLowerCase()) {
            case "education" -> professionalDetailsService.updateEducation(userId, id, (EducationDto) detail);
            case "experience" -> professionalDetailsService.updateExperience(userId, id, (ExperienceDto) detail);
            case "conference" -> professionalDetailsService.updateConference(userId, id.intValue(), ((ConferenceDto) detail).getConference());
            default -> ResponseEntity.badRequest().body("Invalid type: " + type);
        };
    }

    // Delete specific data (education, experience, or conference)
    @DeleteMapping("/{type}/{id}")
    public ResponseEntity<?> deleteProfessionalDetail(
            @PathVariable("userId") Long userId,
            @PathVariable("type") String type,
            @PathVariable("id") Long id) { // For conferences, this will be the index
        return switch (type.toLowerCase()) {
            case "education" -> professionalDetailsService.deleteEducation(userId, id);
            case "experience" -> professionalDetailsService.deleteExperience(userId, id);
            case "conference" -> professionalDetailsService.deleteConference(userId, id.intValue());
            default -> ResponseEntity.badRequest().body("Invalid type: " + type);
        };
    }
}