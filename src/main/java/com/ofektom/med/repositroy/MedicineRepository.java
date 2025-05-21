package com.ofektom.med.repositroy;

import com.ofektom.med.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    List<Medicine> findByNameContainingIgnoringCaseOrCategoryContainingIgnoringCaseOrDescriptionContainingIgnoringCaseOrStatusContainingIgnoringCase(
            String name, String category, String description, String status);
}
