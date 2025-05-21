package com.ofektom.med.controller;

import com.ofektom.med.dto.response.ApiResponse;
import com.ofektom.med.dto.response.PaginatedResponse;
import com.ofektom.med.dto.response.UserResponse;
import com.ofektom.med.dto.response.PrescriptionResponse;
import com.ofektom.med.dto.response.AppointmentResponse;
import com.ofektom.med.dto.response.MedicineResponse;
import com.ofektom.med.dto.response.RoomAllotmentResponse;
import com.ofektom.med.dto.response.PaymentsResponse;
import com.ofektom.med.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<PaginatedResponse<UserResponse>>> searchUsers(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        ApiResponse<PaginatedResponse<UserResponse>> response = searchService.searchUsers(query, pageable);
        return ResponseEntity.status(response.status_code()).body(response);
    }

    @GetMapping("/prescriptions")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> searchPrescriptions(
            @RequestParam(required = false) String query) {
        ApiResponse<PrescriptionResponse> response = searchService.searchPrescriptions(query);
        return ResponseEntity.status(response.status_code()).body(response);
    }

    @GetMapping("/appointments")
    public ResponseEntity<ApiResponse<PaginatedResponse<AppointmentResponse>>> searchAppointments(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        ApiResponse<PaginatedResponse<AppointmentResponse>> response = searchService.searchAppointments(query, pageable);
        return ResponseEntity.status(response.status_code()).body(response);
    }

    @GetMapping("/medicines")
    public ResponseEntity<ApiResponse<PaginatedResponse<MedicineResponse>>> searchMedicines(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        ApiResponse<PaginatedResponse<MedicineResponse>> response = searchService.searchMedicines(query, pageable);
        return ResponseEntity.status(response.status_code()).body(response);
    }

    @GetMapping("/room-allotments")
    public ResponseEntity<ApiResponse<PaginatedResponse<RoomAllotmentResponse>>> searchRoomAllotments(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        ApiResponse<PaginatedResponse<RoomAllotmentResponse>> response = searchService.searchRoomAllotments(query, pageable);
        return ResponseEntity.status(response.status_code()).body(response);
    }

    @GetMapping("/payments")
    public ResponseEntity<ApiResponse<PaginatedResponse<PaymentsResponse>>> searchPayments(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        ApiResponse<PaginatedResponse<PaymentsResponse>> response = searchService.searchPayments(query, pageable);
        return ResponseEntity.status(response.status_code()).body(response);
    }
}