package com.ofektom.med.serviceImpl;

import com.ofektom.med.dto.request.ProfessionalDetailsDto;
import com.ofektom.med.dto.response.ApiResponse;
import com.ofektom.med.dto.response.EducationResponse;
import com.ofektom.med.dto.response.ExperienceResponse;
import com.ofektom.med.dto.response.ProfessionalDetailsResponse;
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
        Optional<ProfessionalDetails> professionalDetailsOpt = professionalDetailsRepository.findByUserId(userId);
        if (professionalDetailsOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Professional details not found for user ID: " + userId, null, null));
        }

        ProfessionalDetails professionalDetails = professionalDetailsOpt.get();
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
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "User not found with ID: " + userId, null, null));
        }

        Optional<ProfessionalDetails> professionalDetailsOpt = professionalDetailsRepository.findByUserId(userId);
        ProfessionalDetails professionalDetails;
        if (professionalDetailsOpt.isPresent()) {
            professionalDetails = professionalDetailsOpt.get();
        } else {
            professionalDetails = new ProfessionalDetails();
            professionalDetails.setUser(userOpt.get());
        }

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
        List<EducationResponse> educationDTOs = professionalDetails.getEducations() != null
                ? professionalDetails.getEducations().stream()
                .map(edu -> new EducationResponse(edu.getId(), edu.getYear(), edu.getDegree(), edu.getInstitute(), edu.getResult()))
                .collect(Collectors.toList())
                : null;

        List<ExperienceResponse> experienceDTOs = professionalDetails.getExperiences() != null
                ? professionalDetails.getExperiences().stream()
                .map(exp -> new ExperienceResponse(exp.getId(), exp.getYear(), exp.getDepartment(), exp.getPosition(), exp.getHospital(), exp.getFeedback()))
                .collect(Collectors.toList())
                : null;

        return new ProfessionalDetailsResponse(
                professionalDetails.getId(),
                professionalDetails.getUser().getId(),
                educationDTOs,
                experienceDTOs,
                professionalDetails.getConferences()
        );
    }
}
