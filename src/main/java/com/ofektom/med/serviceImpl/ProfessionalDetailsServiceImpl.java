package com.ofektom.med.serviceImpl;

import com.ofektom.med.dto.request.EducationDto;
import com.ofektom.med.dto.request.ExperienceDto;
import com.ofektom.med.dto.response.*;
import com.ofektom.med.exception.NotFoundException;
import com.ofektom.med.model.Education;
import com.ofektom.med.model.Experience;
import com.ofektom.med.model.ProfessionalDetails;
import com.ofektom.med.model.User;
import com.ofektom.med.repositroy.ProfessionalDetailsRepository;
import com.ofektom.med.repositroy.UserRepository;
import com.ofektom.med.service.ProfessionalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessionalDetailsServiceImpl implements ProfessionalDetailsService {
    private final UserRepository userRepository;
    private final ProfessionalDetailsRepository professionalDetailsRepository;

    @Autowired
    public ProfessionalDetailsServiceImpl(UserRepository userRepository, ProfessionalDetailsRepository professionalDetailsRepository) {
        this.userRepository = userRepository;
        this.professionalDetailsRepository = professionalDetailsRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<?> getProfessionalDetailsByUserId(Long userId) {
        ProfessionalDetails professionalDetails = professionalDetailsRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Professional details not found for user ID " + userId));

        return ResponseEntity.ok(new ApiResponse<>(200, "Professional details retrieved successfully", null, mapToProfessionalDetailsResponse(professionalDetails)));
    }

    private ProfessionalDetails getOrCreateProfessionalDetails(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));
        return professionalDetailsRepository.findByUserId(userId)
                .orElseGet(() -> {
                    ProfessionalDetails newDetails = new ProfessionalDetails();
                    newDetails.setUser(user);
                    return professionalDetailsRepository.save(newDetails);
                });
    }

    @Override
    @Transactional
    public ResponseEntity<?> addEducation(Long userId, EducationDto educationDto) {
        ProfessionalDetails professionalDetails = getOrCreateProfessionalDetails(userId);
        Education education = new Education();
        education.setYear(educationDto.getYear());
        education.setDegree(educationDto.getDegree());
        education.setInstitute(educationDto.getInstitute());
        education.setResult(educationDto.getResult());
        education.setProfessionalDetails(professionalDetails);
        professionalDetails.getEducations().add(education);
        professionalDetailsRepository.save(professionalDetails);
        return ResponseEntity.ok(new ApiResponse<>(200, "Education added successfully", null, mapToEducationResponse(education)));
    }

    @Override
    @Transactional
    public ResponseEntity<?> addExperience(Long userId, ExperienceDto experienceDto) {
        ProfessionalDetails professionalDetails = getOrCreateProfessionalDetails(userId);
        Experience experience = new Experience();
        experience.setYear(experienceDto.getYear());
        experience.setDepartment(experienceDto.getDepartment());
        experience.setPosition(experienceDto.getPosition());
        experience.setHospital(experienceDto.getHospital());
        experience.setFeedback(experienceDto.getFeedback());
        experience.setProfessionalDetails(professionalDetails);
        professionalDetails.getExperiences().add(experience);
        professionalDetailsRepository.save(professionalDetails);
        return ResponseEntity.ok(new ApiResponse<>(200, "Experience added successfully", null, mapToExperienceResponse(experience)));
    }

    @Override
    @Transactional
    public ResponseEntity<?> addConference(Long userId, String conference) {
        ProfessionalDetails professionalDetails = getOrCreateProfessionalDetails(userId);
        professionalDetails.getConferences().add(conference);
        professionalDetailsRepository.save(professionalDetails);
        return ResponseEntity.ok(new ApiResponse<>(200, "Conference added successfully", null, conference));
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateEducation(Long userId, Long educationId, EducationDto educationDto) {
        ProfessionalDetails professionalDetails = getOrCreateProfessionalDetails(userId);
        Education education = professionalDetails.getEducations().stream()
                .filter(e -> e.getId().equals(educationId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Education with ID " + educationId + " not found"));
        education.setYear(educationDto.getYear());
        education.setDegree(educationDto.getDegree());
        education.setInstitute(educationDto.getInstitute());
        education.setResult(educationDto.getResult());
        professionalDetailsRepository.save(professionalDetails);
        return ResponseEntity.ok(new ApiResponse<>(200, "Education updated successfully", null, mapToEducationResponse(education)));
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateExperience(Long userId, Long experienceId, ExperienceDto experienceDto) {
        ProfessionalDetails professionalDetails = getOrCreateProfessionalDetails(userId);
        Experience experience = professionalDetails.getExperiences().stream()
                .filter(e -> e.getId().equals(experienceId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Experience with ID " + experienceId + " not found"));
        experience.setYear(experienceDto.getYear());
        experience.setDepartment(experienceDto.getDepartment());
        experience.setPosition(experienceDto.getPosition());
        experience.setHospital(experienceDto.getHospital());
        experience.setFeedback(experienceDto.getFeedback());
        professionalDetailsRepository.save(professionalDetails);
        return ResponseEntity.ok(new ApiResponse<>(200, "Experience updated successfully", null, mapToExperienceResponse(experience)));
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateConference(Long userId, int index, String conference) {
        ProfessionalDetails professionalDetails = getOrCreateProfessionalDetails(userId);
        List<String> conferences = professionalDetails.getConferences();
        if (index < 0 || index >= conferences.size()) {
            throw new NotFoundException("Conference at index " + index + " not found");
        }
        conferences.set(index, conference);
        professionalDetailsRepository.save(professionalDetails);
        return ResponseEntity.ok(new ApiResponse<>(200, "Conference updated successfully", null, conference));
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteEducation(Long userId, Long educationId) {
        ProfessionalDetails professionalDetails = getOrCreateProfessionalDetails(userId);
        Education education = professionalDetails.getEducations().stream()
                .filter(e -> e.getId().equals(educationId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Education with ID " + educationId + " not found"));
        professionalDetails.getEducations().remove(education);
        professionalDetailsRepository.save(professionalDetails);
        return ResponseEntity.ok(new ApiResponse<>(200, "Education deleted successfully", null, null));
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteExperience(Long userId, Long experienceId) {
        ProfessionalDetails professionalDetails = getOrCreateProfessionalDetails(userId);
        Experience experience = professionalDetails.getExperiences().stream()
                .filter(e -> e.getId().equals(experienceId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Experience with ID " + experienceId + " not found"));
        professionalDetails.getExperiences().remove(experience);
        professionalDetailsRepository.save(professionalDetails);
        return ResponseEntity.ok(new ApiResponse<>(200, "Experience deleted successfully", null, null));
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteConference(Long userId, int index) {
        ProfessionalDetails professionalDetails = getOrCreateProfessionalDetails(userId);
        List<String> conferences = professionalDetails.getConferences();
        if (index < 0 || index >= conferences.size()) {
            throw new NotFoundException("Conference at index " + index + " not found");
        }
        String removedConference = conferences.remove(index);
        professionalDetailsRepository.save(professionalDetails);
        return ResponseEntity.ok(new ApiResponse<>(200, "Conference deleted successfully", null, removedConference));
    }

    private ProfessionalDetailsResponse mapToProfessionalDetailsResponse(ProfessionalDetails professionalDetails) {
        List<EducationResponse> educationDTOs = professionalDetails.getEducations() != null
                ? professionalDetails.getEducations().stream()
                .map(edu -> new EducationResponse(edu.getId(), edu.getYear(), edu.getDegree(), edu.getInstitute(), edu.getResult()))
                .collect(Collectors.toList())
                : Collections.emptyList();

        List<ExperienceResponse> experienceDTOs = professionalDetails.getExperiences() != null
                ? professionalDetails.getExperiences().stream()
                .map(exp -> new ExperienceResponse(exp.getId(), exp.getYear(), exp.getDepartment(), exp.getPosition(), exp.getHospital(), exp.getFeedback()))
                .collect(Collectors.toList())
                : Collections.emptyList();

        return new ProfessionalDetailsResponse(
                professionalDetails.getId(),
                professionalDetails.getUser().getId(),
                educationDTOs,
                experienceDTOs,
                professionalDetails.getConferences() != null ? professionalDetails.getConferences() : Collections.emptyList()
        );
    }

    private ExperienceResponse mapToExperienceResponse(Experience exp){
        return  new ExperienceResponse(exp.getId(), exp.getYear(), exp.getDepartment(), exp.getPosition(), exp.getHospital(), exp.getFeedback());
    }

    private EducationResponse mapToEducationResponse(Education edu){
        return  new EducationResponse(edu.getId(), edu.getYear(), edu.getDegree(), edu.getInstitute(), edu.getResult());
    }
}