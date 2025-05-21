package com.ofektom.med.serviceImpl;

import com.ofektom.med.dto.request.RoomAllotmentDto;
import com.ofektom.med.dto.response.AddressResponse;
import com.ofektom.med.dto.response.RoomAllotmentResponse;
import com.ofektom.med.dto.response.UserResponse;
import com.ofektom.med.exception.NotFoundException;
import com.ofektom.med.model.RoomAllotment;
import com.ofektom.med.model.User;
import com.ofektom.med.repositroy.RoomAllotmentRepository;
import com.ofektom.med.repositroy.UserRepository;
import com.ofektom.med.service.RoomAllotmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomAllotmentServiceImpl implements RoomAllotmentService {

    private final RoomAllotmentRepository roomAllotmentRepository;

    private final UserRepository userRepository;

    @Autowired
    public RoomAllotmentServiceImpl(RoomAllotmentRepository roomAllotmentRepository, UserRepository userRepository) {
        this.roomAllotmentRepository = roomAllotmentRepository;
        this.userRepository = userRepository ;
    }

    @Override
    public RoomAllotmentResponse allotRoom(RoomAllotmentDto roomAllotmentDto) {
        if (roomAllotmentRepository.existsByRoomNoAndPatientId(roomAllotmentDto.getRoomNo(), roomAllotmentDto.getPatientId())) {
            throw new IllegalStateException("Room is already allotted to this patient");
        }

        User patient = userRepository.findById(roomAllotmentDto.getPatientId())
                .orElseThrow(() -> new NotFoundException("Patient not found"));

        RoomAllotment roomAllotment = new RoomAllotment();
        roomAllotment.setRoomNo(roomAllotmentDto.getRoomNo());
        roomAllotment.setRoomType(roomAllotmentDto.getRoomType());
        roomAllotment.setPatient(patient);
        roomAllotment.setAllotmentDate(roomAllotmentDto.getAllotmentDate());
        roomAllotment.setDischargeDate(roomAllotmentDto.getDischargeDate());

        roomAllotment = roomAllotmentRepository.save(roomAllotment);
        return mapToResponse(roomAllotment);
    }

    @Override
    public RoomAllotmentResponse updateRoomAllotment(Long id, RoomAllotmentDto roomAllotmentDto) {
        RoomAllotment roomAllotment = roomAllotmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Room allotment not found"));

        User patient = userRepository.findById(roomAllotmentDto.getPatientId())
                .orElseThrow(() -> new NotFoundException("Patient not found"));

        roomAllotment.setRoomNo(roomAllotmentDto.getRoomNo());
        roomAllotment.setRoomType(roomAllotmentDto.getRoomType());
        roomAllotment.setPatient(patient);
        roomAllotment.setAllotmentDate(roomAllotmentDto.getAllotmentDate());
        roomAllotment.setDischargeDate(roomAllotmentDto.getDischargeDate());

        roomAllotment = roomAllotmentRepository.save(roomAllotment);
        return mapToResponse(roomAllotment);
    }

    @Override
    public void deleteRoomAllotment(Long id) {
        RoomAllotment roomAllotment = roomAllotmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Room allotment not found"));
        roomAllotmentRepository.delete(roomAllotment);
    }

    @Override
    public List<RoomAllotmentResponse> getAllAllotedRooms() {
        return roomAllotmentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RoomAllotmentResponse getRoomAllotmentById(Long id) {
        RoomAllotment roomAllotment = roomAllotmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Room allotment not found"));
        return mapToResponse(roomAllotment);
    }

    private RoomAllotmentResponse mapToResponse(RoomAllotment roomAllotment) {
        User patient = roomAllotment.getPatient();
        AddressResponse patientAddress = patient.getAddress() != null ? new AddressResponse(
                patient.getAddress().getStreetAddress(),
                patient.getAddress().getCity(),
                patient.getAddress().getState(),
                patient.getAddress().getCountry()
        ) : null;
        UserResponse userResponse = new UserResponse(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getMembershipNo(),
                patient.getEmail(),
                patient.getPhoneNumber(),
                patient.getGender(),
                patient.getStatus(),
                patient.getProfilePicture(),
                patient.getDesignation(),
                patient.getDepartment(),
                patient.getMaritalStatus(),
                patient.getBloodGroup(),
                patient.getBloodPressure(),
                patient.getSugar(),
                patient.getInjuryCondition(),
                patient.getAllergies(),
                patientAddress,
                patient.getUserRole().name(),
                patient.getLastLogin() != null ? patient.getLastLogin().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null,
                patient.getLoginDuration(),
                patient.getSpeciality(),
                patient.getAbout(),
                patient.getCreatedAt()
        );

        return new RoomAllotmentResponse(
                roomAllotment.getId(),
                roomAllotment.getRoomNo(),
                roomAllotment.getRoomType(),
                userResponse,
                roomAllotment.getAllotmentDate(),
                roomAllotment.getDischargeDate(),
                null,
                roomAllotment.getCreatedAt(),
                roomAllotment.getUpdatedAt()
        );
    }
}
