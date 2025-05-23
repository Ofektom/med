package com.ofektom.med.serviceImpl;

import com.ofektom.med.dto.request.ProfessionalDetailsDto;
import com.ofektom.med.dto.response.ApiResponse;
import com.ofektom.med.dto.response.EducationResponse;
import com.ofektom.med.dto.response.ExperienceResponse;
import com.ofektom.med.dto.response.ProfessionalDetailsResponse;
import com.ofektom.med.exception.NotFoundException;
import com.ofektom.med.model.Education;
import com.ofektom.med.model.Experience;
import com.ofektom.med.model.ProfessionalDetails;
import com.ofektom.med.model.User;
import com.ofektom.med.repositroy.ProfessionalDetailsRepository;
import com.ofektom.med.repositroy.UserRepository;
import com.ofektom.med.service.ProfessionalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfessionalDetailsServiceImpl implements ProfessionalDetailsService {
    private final ProfessionalDetailsRepository professionalDetailsRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProfessionalDetailsServiceImpl(ProfessionalDetailsRepository professionalDetailsRepository, UserRepository userRepository) {
        this.professionalDetailsRepository = professionalDetailsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> getProfessionalDetailsByUserId(Long userId) {
        ProfessionalDetails professionalDetails = professionalDetailsRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("Professional details not found for user ID: " + userId));
        ProfessionalDetailsResponse professionalDetailsResponse = mapToProfessionalDetailsResponse(professionalDetails);
        ApiResponse<ProfessionalDetailsResponse> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Professional details retrieved successfully",
                null,
                professionalDetailsResponse
        );
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> updateProfessionalDetails(Long userId, ProfessionalDetailsDto professionalDetailsDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

        ProfessionalDetails professionalDetails = professionalDetailsRepository.findByUserId(userId).orElseGet(() -> {
                    ProfessionalDetails newDetails = new ProfessionalDetails();
                    newDetails.setUser(user);
                    return newDetails;
                });

        // Update educations
        if (professionalDetailsDTO.getEducations() != null) {
            List<Education> educations = professionalDetailsDTO.getEducations().stream()
                    .map(dto -> new Education(dto.getYear(), dto.getDegree(), dto.getInstitute(), dto.getResult()))
                    .collect(Collectors.toList());
            professionalDetails.setEducations(educations);
        }

        // Update experiences
        if (professionalDetailsDTO.getExperiences() != null) {
            List<Experience> experiences = professionalDetailsDTO.getExperiences().stream()
                    .map(dto -> new Experience(dto.getYear(), dto.getDepartment(), dto.getPosition(), dto.getHospital(), dto.getFeedback()))
                    .collect(Collectors.toList());
            professionalDetails.setExperiences(experiences);
        }

        // Update conferences
        if (professionalDetailsDTO.getConferences() != null) {
            professionalDetails.setConferences(professionalDetailsDTO.getConferences());
        }

        professionalDetailsRepository.save(professionalDetails);
        ProfessionalDetailsResponse professionalDetailsResponse = mapToProfessionalDetailsResponse(professionalDetails);
        ApiResponse<ProfessionalDetailsResponse> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Professional details updated successfully",
                null,
                professionalDetailsResponse
        );
        return ResponseEntity.ok(response);
    }

    private ProfessionalDetailsResponse mapToProfessionalDetailsResponse(ProfessionalDetails professionalDetails) {
        List<EducationResponse> educationResponses = professionalDetails.getEducations() != null
                ? professionalDetails.getEducations().stream()
                .map(edu -> new EducationResponse(edu.getId(), edu.getYear(), edu.getDegree(), edu.getInstitute(), edu.getResult()))
                .collect(Collectors.toList())
                : null;

        List<ExperienceResponse> experienceResponses = professionalDetails.getExperiences() != null
                ? professionalDetails.getExperiences().stream()
                .map(exp -> new ExperienceResponse(exp.getId(), exp.getYear(), exp.getDepartment(), exp.getPosition(), exp.getHospital(), exp.getFeedback()))
                .collect(Collectors.toList())
                : null;

        return new ProfessionalDetailsResponse(
                professionalDetails.getId(),
                professionalDetails.getUser().getId(),
                educationResponses,
                experienceResponses,
                professionalDetails.getConferences()
        );
    }
}
