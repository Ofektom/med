package com.ofektom.med.repositroy;

import com.ofektom.med.model.ProfessionalDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessionalDetailsRepository extends JpaRepository<ProfessionalDetails, Long> {
    Optional<ProfessionalDetails> findByUserId(Long userId);
}
