package com.ofektom.med.service;

import com.ofektom.med.dto.response.*;
import org.springframework.data.domain.Pageable;

public interface SearchService {
    ApiResponse<PaginatedResponse<UserResponse>> searchUsers(String query, Pageable pageable);
    ApiResponse<PrescriptionResponse> searchPrescriptions(String query);
    ApiResponse<PaginatedResponse<AppointmentResponse>> searchAppointments(String query, Pageable pageable);
    ApiResponse<PaginatedResponse<MedicineResponse>> searchMedicines(String query, Pageable pageable);
    ApiResponse<PaginatedResponse<RoomAllotmentResponse>> searchRoomAllotments(String query, Pageable pageable);
    ApiResponse<PaginatedResponse<PaymentsResponse>> searchPayments(String query, Pageable pageable);
}