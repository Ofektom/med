package com.ofektom.med.service;

import com.ofektom.med.dto.request.ProfessionalDetailsDto;
import org.springframework.http.ResponseEntity;

public interface ProfessionalDetailsService {
    ResponseEntity<?> getProfessionalDetailsByUserId(Long userId);

    ResponseEntity<?> updateProfessionalDetails(Long userId, ProfessionalDetailsDto professionalDetailsDTO);
}
