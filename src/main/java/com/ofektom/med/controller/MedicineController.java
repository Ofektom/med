package com.ofektom.med.controller;

import com.ofektom.med.dto.request.MedicineDto;
import com.ofektom.med.dto.response.ApiResponse;
import com.ofektom.med.dto.response.MedicineResponse;
import com.ofektom.med.dto.response.PaginatedResponse;
import com.ofektom.med.dto.response.UserResponse;
import com.ofektom.med.service.MedicineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.util.XMLEventConsumer;
import java.util.List;

@RestController
@RequestMapping("/api/v1/medication")
public class MedicineController {

    private final MedicineService medicineService;

    @Autowired
    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMedicine(@Valid @RequestBody MedicineDto medicineDto) {
        return medicineService.addMedicine(medicineDto);
    }

    @GetMapping("/{med_id}")
    public ResponseEntity<?> getMedicine(@PathVariable("med_id") Long id){
        return medicineService.getMedicineById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getUsers(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        if (page == null || size == null) {
            List<MedicineResponse> meds = medicineService.getAllMedicineAsList();
            ApiResponse<List<MedicineResponse>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Full list of medicine retrieved successfully",
                    null,
                    meds
            );
            return ResponseEntity.ok(response);
        } else {
            Pageable pageable = PageRequest.of(page, size);
            Page<MedicineResponse> usersPage = medicineService.getAllMedicineAsPage(pageable);
            PaginatedResponse<MedicineResponse> paginatedData = new PaginatedResponse<>(
                    usersPage.getNumber(),
                    usersPage.getSize(),
                    usersPage.getTotalPages(),
                    usersPage.getTotalElements(),
                    usersPage.getContent()
            );
            ApiResponse<PaginatedResponse<MedicineResponse>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Paginated list of medicine retrieved successfully",
                    null,
                    paginatedData
            );
            return ResponseEntity.ok(response);
        }
    }
}