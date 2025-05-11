package com.ofektom.med.serviceImpl;

import com.ofektom.med.dto.request.MedicineDto;
import com.ofektom.med.dto.response.ApiResponse;
import com.ofektom.med.dto.response.MedicineResponse;
import com.ofektom.med.dto.response.UserResponse;
import com.ofektom.med.enums.Role;
import com.ofektom.med.exception.NotFoundException;
import com.ofektom.med.model.Medicine;
import com.ofektom.med.model.User;
import com.ofektom.med.repositroy.MedicineRepository;
import com.ofektom.med.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;

    @Autowired
    public MedicineServiceImpl(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    @Override
    public ResponseEntity<?> addMedicine(MedicineDto medicineDto) {
        Medicine medicine = new Medicine();
        medicine.setName(medicineDto.getName());
        medicine.setCategory(medicineDto.getCategory());
        medicine.setDescription(medicineDto.getDescription());
        medicine.setExpiryDate(medicineDto.getExpiryDate());
        medicine.setPrice(medicineDto.getPrice());
        medicine.setStatus(medicineDto.getStatus());

        medicine = medicineRepository.save(medicine);

        MedicineResponse data = getMedicineResponse(medicine);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Medicine added successfully", null, data));
    }

    @Override
    public Page<MedicineResponse> getAllMedicine(Pageable pageable) {
        Page<Medicine> medicinePage = medicineRepository.findAll(pageable);
        return medicinePage.map(this::getMedicineResponse);
    }

    @Override
    public List<MedicineResponse> getAllMedicineAsList() {
        List<Medicine> meds;
        meds = medicineRepository.findAll();
        return meds.stream().map(this::getMedicineResponse).collect(Collectors.toList());
    }

    @Override
    public Page<MedicineResponse> getAllMedicineAsPage(Pageable pageable) {
        Page<Medicine> medsPage;
        medsPage = medicineRepository.findAll(pageable);
        return medsPage.map(this::getMedicineResponse);
    }

    @Override
    public ResponseEntity<?> getMedicineById(Long id) {
        Medicine medicine = medicineRepository.findById(id).orElseThrow(() -> new NotFoundException("Medicine not Found"));
        MedicineResponse data = getMedicineResponse(medicine);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Medicine fetched successfully", null, data));
    }

    private MedicineResponse getMedicineResponse(Medicine medicine){
        return new MedicineResponse(
                medicine.getId(),
                medicine.getName(),
                medicine.getCategory(),
                medicine.getDescription(),
                medicine.getPrice(),
                medicine.getExpiryDate(),
                medicine.getStatus(),
                medicine.getCreatedAt()
        );
    }
}
