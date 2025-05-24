package com.ofektom.med.service;

import com.ofektom.med.dto.request.EducationDto;
import com.ofektom.med.dto.request.ExperienceDto;
import com.ofektom.med.dto.request.ProfessionalDetailsDto;
import org.springframework.http.ResponseEntity;

public interface ProfessionalDetailsService {
    ResponseEntity<?> getProfessionalDetailsByUserId(Long userId);
    ResponseEntity<?> addEducation(Long userId, EducationDto educationDto);
    ResponseEntity<?> addExperience(Long userId, ExperienceDto experienceDto);
    ResponseEntity<?> addConference(Long userId, String conference);
    ResponseEntity<?> updateEducation(Long userId, Long educationId, EducationDto educationDto);
    ResponseEntity<?> updateExperience(Long userId, Long experienceId, ExperienceDto experienceDto);
    ResponseEntity<?> updateConference(Long userId, int index, String conference);
    ResponseEntity<?> deleteEducation(Long userId, Long educationId);
    ResponseEntity<?> deleteExperience(Long userId, Long experienceId);
    ResponseEntity<?> deleteConference(Long userId, int index);
}
