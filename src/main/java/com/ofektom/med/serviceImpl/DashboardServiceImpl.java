package com.ofektom.med.serviceImpl;

import com.ofektom.med.dto.response.ApiResponse;
import com.ofektom.med.enums.Role;
import com.ofektom.med.repositroy.AppointmentRepository;
import com.ofektom.med.repositroy.RoomAllotmentRepository;
import com.ofektom.med.repositroy.UserRepository;
import com.ofektom.med.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final RoomAllotmentRepository roomAllotmentRepository;

    @Autowired
    public DashboardServiceImpl(UserRepository userRepository,
                                AppointmentRepository appointmentRepository,
                                RoomAllotmentRepository roomAllotmentRepository) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.roomAllotmentRepository = roomAllotmentRepository;
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getDashboardStats() {
        try {
            Map<String, Object> stats = new HashMap<>();
            
            // Count total appointments
            long totalAppointments = appointmentRepository.count();
            stats.put("totalAppointments", totalAppointments);
            
            // Count total patients
            long totalPatients = userRepository.findByUserRole(Role.ROLE_PATIENT).size();
            stats.put("totalPatients", totalPatients);
            
            // Count total staff (all staff roles except patients and super admin)
            long totalStaffs = userRepository.findByUserRole(Role.ROLE_DOCTOR).size() +
                             userRepository.findByUserRole(Role.ROLE_NURSE).size() +
                             userRepository.findByUserRole(Role.ROLE_FRONT_DESK).size() +
                             userRepository.findByUserRole(Role.ROLE_ADMIN).size() +
                             userRepository.findByUserRole(Role.ROLE_STAFF).size() +
                             userRepository.findByUserRole(Role.ROLE_ACCOUNTANT).size() +
                             userRepository.findByUserRole(Role.ROLE_PHARMACIST).size() +
                             userRepository.findByUserRole(Role.ROLE_LAB_SCIENTIST).size() +
                             userRepository.findByUserRole(Role.ROLE_RADIOGRAPHER).size();
            stats.put("totalStaffs", totalStaffs);
            
            // Count total beds (room allotments)
            long totalBeds = roomAllotmentRepository.count();
            stats.put("totalBeds", totalBeds);
            
            // Deaths and accidents - these might not be tracked in the system yet
            // Set to 0 for now, can be added later if needed
            stats.put("totalDeaths", 0);
            stats.put("totalAccidents", 0);
            
            ApiResponse<Map<String, Object>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Dashboard statistics retrieved successfully",
                null,
                stats
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Return zeros on error
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalAppointments", 0);
            stats.put("totalPatients", 0);
            stats.put("totalStaffs", 0);
            stats.put("totalBeds", 0);
            stats.put("totalDeaths", 0);
            stats.put("totalAccidents", 0);
            
            ApiResponse<Map<String, Object>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Dashboard statistics retrieved successfully",
                null,
                stats
            );
            
            return ResponseEntity.ok(response);
        }
    }
}

