package com.ofektom.med.service;

import com.ofektom.med.dto.request.MedicineDto;
import com.ofektom.med.dto.response.MedicineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MedicineService {
    ResponseEntity<?> addMedicine(MedicineDto medicineDto);
    Page<MedicineResponse> getAllMedicine(Pageable pageable);
    ResponseEntity<?> getMedicineById(Long id);
    List<MedicineResponse> getAllMedicineAsList();
    Page<MedicineResponse> getAllMedicineAsPage(Pageable pageable);
}
