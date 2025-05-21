package com.ofektom.med.serviceImpl;

//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.elasticsearch._types.FieldValue;
//import co.elastic.clients.elasticsearch._types.query_dsl.*;
//import co.elastic.clients.elasticsearch.core.SearchRequest;
//import co.elastic.clients.elasticsearch.core.SearchResponse;

import com.ofektom.med.dto.response.*;
import com.ofektom.med.enums.RoomType;
import com.ofektom.med.exception.NotFoundException;
import com.ofektom.med.model.Appointment;
import com.ofektom.med.model.Prescription;
import com.ofektom.med.model.User;
import com.ofektom.med.repositroy.*;
import com.ofektom.med.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;



import com.ofektom.med.model.Medicine;
import com.ofektom.med.model.RoomAllotment;
import com.ofektom.med.model.Payments;


import java.util.*;

@Service
public class SearchServiceImpl implements SearchService {

    private final UserRepository userRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicineRepository medicineRepository;
    private final RoomAllotmentRepository roomAllotmentRepository;
    private final PaymentsRepository paymentsRepository;

    @Autowired
    public SearchServiceImpl(UserRepository userRepository,
                             PrescriptionRepository prescriptionRepository,
                             AppointmentRepository appointmentRepository,
                             MedicineRepository medicineRepository,
                             RoomAllotmentRepository roomAllotmentRepository,
                             PaymentsRepository paymentsRepository) {
        this.userRepository = userRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicineRepository = medicineRepository;
        this.roomAllotmentRepository = roomAllotmentRepository;
        this.paymentsRepository = paymentsRepository;
    }


    @Override
    public ApiResponse<PaginatedResponse<UserResponse>> searchUsers(String query, Pageable pageable) {
        try {
            List<User> personalResults = userRepository.findByFirstNameContainingIgnoringCaseOrLastNameContainingIgnoringCase(query, query).orElseThrow(() -> new NotFoundException("User Not Found"));
            List<User> contactResults = userRepository.findByEmailContainingIgnoringCaseOrMembershipNoContainingIgnoringCase(query, query).orElseThrow(() -> new NotFoundException("User Not Found"));
            List<User> professionalResults = userRepository.findByDesignationContainingIgnoringCaseOrDepartmentContainingIgnoringCase(query, query).orElseThrow(() -> new NotFoundException("User Not Found"));

            List<User> allUsers = new ArrayList<>();
            allUsers.addAll(personalResults != null ? personalResults : Collections.emptyList());
            allUsers.addAll(contactResults != null ? contactResults : Collections.emptyList());
            allUsers.addAll(professionalResults != null ? professionalResults : Collections.emptyList());
            allUsers = allUsers.stream().distinct().collect(Collectors.toList());

            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), allUsers.size());
            List<User> pagedUsers = allUsers.subList(start, end);

            List<UserResponse> userResponses = pagedUsers.stream()
                    .map(user -> {
                        AddressResponse addressResponse = null;
                        if (user.getAddress() != null) {
                            addressResponse = new AddressResponse(
                                    user.getAddress().getStreetAddress(),
                                    user.getAddress().getCity(),
                                    user.getAddress().getState(),
                                    user.getAddress().getCountry()
                            );
                        }
                        return new UserResponse(
                                user.getId(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.getMembershipNo(),
                                user.getEmail(),
                                user.getPhoneNumber(),
                                user.getGender(),
                                user.getStatus(),
                                user.getProfilePicture(),
                                user.getDesignation(),
                                user.getDepartment(),
                                user.getMaritalStatus(),
                                user.getBloodGroup(),
                                user.getBloodPressure(),
                                user.getSugar(),
                                user.getInjuryCondition(),
                                user.getAllergies(),
                                addressResponse,
                                user.getUserRole().name(),
                                user.getLastLogin() != null ? user.getLastLogin().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null,
                                user.getLoginDuration(),
                                user.getSpeciality(),
                                user.getAbout(),
                                user.getCreatedAt()
                        );
                    })
                    .collect(Collectors.toList());

            PaginatedResponse<UserResponse> paginatedData = new PaginatedResponse<>(
                    pageable.getPageNumber() + 1, // Adjust to one-based page number
                    pageable.getPageSize(),
                    (int) Math.ceil((double) allUsers.size() / pageable.getPageSize()),
                    allUsers.size(),
                    userResponses
            );

            return new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Users retrieved successfully",
                    null,
                    paginatedData
            );
        } catch (Exception e) {
            return new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error performing user search: " + e.getMessage(),
                    null,
                    null
            );
        }
    }

    @Override
    public ApiResponse<PrescriptionResponse> searchPrescriptions(String query) {
        try {
            Optional<Prescription> prescriptionResult = prescriptionRepository.findFirstByMedicationContainingIgnoringCaseOrDosageContainingIgnoringCaseOrInstructionsContainingIgnoringCase(query, query, query);
            Optional<Prescription> doctorResult = prescriptionRepository.findFirstByAppointmentDoctorFirstNameContainingIgnoringCaseOrAppointmentDoctorLastNameContainingIgnoringCase(query, query);
            Optional<Prescription> patientResult = prescriptionRepository.findFirstByAppointmentPatientFirstNameContainingIgnoringCaseOrAppointmentPatientLastNameContainingIgnoringCase(query, query);

            Optional<Prescription> finalResult = prescriptionResult
                    .or(() -> doctorResult)
                    .or(() -> patientResult);

            if (finalResult.isEmpty()) {
                return new ApiResponse<>(
                        HttpStatus.NOT_FOUND.value(),
                        "No prescription found for query: " + query,
                        null,
                        null
                );
            }

            Prescription prescription = finalResult.get();
            AppointmentResponse appointmentResponse = null;
            if (prescription.getAppointment() != null) {
                Appointment appointment = prescription.getAppointment();
                UserResponse doctorResponse = null;
                if (appointment.getDoctor() != null) {
                    User doctor = appointment.getDoctor();
                    AddressResponse doctorAddress = doctor.getAddress() != null ? new AddressResponse(
                            doctor.getAddress().getStreetAddress(),
                            doctor.getAddress().getCity(),
                            doctor.getAddress().getState(),
                            doctor.getAddress().getCountry()
                    ) : null;
                    doctorResponse = new UserResponse(
                            doctor.getId(),
                            doctor.getFirstName(),
                            doctor.getLastName(),
                            doctor.getMembershipNo(),
                            doctor.getEmail(),
                            doctor.getPhoneNumber(),
                            doctor.getGender(),
                            doctor.getStatus(),
                            doctor.getProfilePicture(),
                            doctor.getDesignation(),
                            doctor.getDepartment(),
                            doctor.getMaritalStatus(),
                            doctor.getBloodGroup(),
                            doctor.getBloodPressure(),
                            doctor.getSugar(),
                            doctor.getInjuryCondition(),
                            doctor.getAllergies(),
                            doctorAddress,
                            doctor.getUserRole().name(),
                            doctor.getLastLogin() != null ? doctor.getLastLogin().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null,
                            doctor.getLoginDuration(),
                            doctor.getSpeciality(),
                            doctor.getAbout(),
                            doctor.getCreatedAt()
                    );
                }
                UserResponse patientResponse = null;
                if (appointment.getPatient() != null) {
                    User patient = appointment.getPatient();
                    AddressResponse patientAddress = patient.getAddress() != null ? new AddressResponse(
                            patient.getAddress().getStreetAddress(),
                            patient.getAddress().getCity(),
                            patient.getAddress().getState(),
                            patient.getAddress().getCountry()
                    ) : null;
                    patientResponse = new UserResponse(
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
                }
                appointmentResponse = new AppointmentResponse(
                        appointment.getId(),
                        appointment.getAppointmentDate(),
                        appointment.getReason(),
                        appointment.getStatus(),
                        doctorResponse,
                        patientResponse,
                        appointment.getFrom(),
                        appointment.getTo(),
                        appointment.getTreatment(),
                        appointment.getNotes(),
                        appointment.getCreatedAt(),
                        appointment.getUpdatedAt()
                );
            }

            PrescriptionResponse prescriptionResponse = new PrescriptionResponse(
                    prescription.getId(),
                    prescription.getMedication(),
                    prescription.getDosage(),
                    prescription.getInstructions(),
                    appointmentResponse,
                    prescription.getCreatedAt(),
                    prescription.getUpdatedAt()
            );

            return new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Prescription retrieved successfully",
                    null,
                    prescriptionResponse
            );
        } catch (Exception e) {
            return new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error performing prescription search: " + e.getMessage(),
                    null,
                    null
            );
        }
    }

    @Override
    public ApiResponse<PaginatedResponse<AppointmentResponse>> searchAppointments(String query, Pageable pageable) {
        try {
            List<Appointment> reasonResults = appointmentRepository.findByReasonContainingIgnoringCaseOrStatusContainingIgnoringCaseOrTreatmentContainingIgnoringCaseOrNotesContainingIgnoringCase(query, query, query, query);
            List<Appointment> doctorResults = appointmentRepository.findByDoctorFirstNameContainingIgnoringCaseOrDoctorLastNameContainingIgnoringCase(query, query);
            List<Appointment> patientResults = appointmentRepository.findByPatientFirstNameContainingIgnoringCaseOrPatientLastNameContainingIgnoringCase(query, query);

            List<Appointment> allAppointments = new ArrayList<>();
            allAppointments.addAll(reasonResults);
            allAppointments.addAll(doctorResults);
            allAppointments.addAll(patientResults);
            allAppointments = allAppointments.stream().distinct().collect(Collectors.toList());

            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), allAppointments.size());
            List<Appointment> pagedAppointments = allAppointments.subList(start, end);

            List<AppointmentResponse> appointmentResponses = pagedAppointments.stream()
                    .map(appointment -> {
                        UserResponse doctorResponse = null;
                        if (appointment.getDoctor() != null) {
                            User doctor = appointment.getDoctor();
                            AddressResponse doctorAddress = doctor.getAddress() != null ? new AddressResponse(
                                    doctor.getAddress().getStreetAddress(),
                                    doctor.getAddress().getCity(),
                                    doctor.getAddress().getState(),
                                    doctor.getAddress().getCountry()
                            ) : null;
                            doctorResponse = new UserResponse(
                                    doctor.getId(),
                                    doctor.getFirstName(),
                                    doctor.getLastName(),
                                    doctor.getMembershipNo(),
                                    doctor.getEmail(),
                                    doctor.getPhoneNumber(),
                                    doctor.getGender(),
                                    doctor.getStatus(),
                                    doctor.getProfilePicture(),
                                    doctor.getDesignation(),
                                    doctor.getDepartment(),
                                    doctor.getMaritalStatus(),
                                    doctor.getBloodGroup(),
                                    doctor.getBloodPressure(),
                                    doctor.getSugar(),
                                    doctor.getInjuryCondition(),
                                    doctor.getAllergies(),
                                    doctorAddress,
                                    doctor.getUserRole().name(),
                                    doctor.getLastLogin() != null ? doctor.getLastLogin().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null,
                                    doctor.getLoginDuration(),
                                    doctor.getSpeciality(),
                                    doctor.getAbout(),
                                    doctor.getCreatedAt()
                            );
                        }
                        UserResponse patientResponse = null;
                        if (appointment.getPatient() != null) {
                            User patient = appointment.getPatient();
                            AddressResponse patientAddress = patient.getAddress() != null ? new AddressResponse(
                                    patient.getAddress().getStreetAddress(),
                                    patient.getAddress().getCity(),
                                    patient.getAddress().getState(),
                                    patient.getAddress().getCountry()
                            ) : null;
                            patientResponse = new UserResponse(
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
                        }
                        return new AppointmentResponse(
                                appointment.getId(),
                                appointment.getAppointmentDate(),
                                appointment.getReason(),
                                appointment.getStatus(),
                                doctorResponse,
                                patientResponse,
                                appointment.getFrom(),
                                appointment.getTo(),
                                appointment.getTreatment(),
                                appointment.getNotes(),
                                appointment.getCreatedAt(),
                                appointment.getUpdatedAt()
                        );
                    })
                    .collect(Collectors.toList());

            PaginatedResponse<AppointmentResponse> paginatedData = new PaginatedResponse<>(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    (int) Math.ceil((double) allAppointments.size() / pageable.getPageSize()),
                    allAppointments.size(),
                    appointmentResponses
            );

            return new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Appointments retrieved successfully",
                    null,
                    paginatedData
            );
        } catch (Exception e) {
            return new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error performing appointment search: " + e.getMessage(),
                    null,
                    null
            );
        }
    }

    @Override
    public ApiResponse<PaginatedResponse<MedicineResponse>> searchMedicines(String query, Pageable pageable) {
        try {
            List<Medicine> medicineResults = medicineRepository.findByNameContainingIgnoringCaseOrCategoryContainingIgnoringCaseOrDescriptionContainingIgnoringCaseOrStatusContainingIgnoringCase(query, query, query, query);

            List<Medicine> allMedicines = medicineResults.stream().distinct().collect(Collectors.toList());

            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), allMedicines.size());
            List<Medicine> pagedMedicines = allMedicines.subList(start, end);

            List<MedicineResponse> medicineResponses = pagedMedicines.stream()
                    .map(medicine -> new MedicineResponse(
                            medicine.getId(),
                            medicine.getName(),
                            medicine.getCategory(),
                            medicine.getDescription(),
                            medicine.getPrice(),
                            medicine.getExpiryDate(),
                            medicine.getStatus(),
                            medicine.getCreatedAt(),
                            medicine.getUpdatedAt()
                    ))
                    .collect(Collectors.toList());

            PaginatedResponse<MedicineResponse> paginatedData = new PaginatedResponse<>(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    (int) Math.ceil((double) allMedicines.size() / pageable.getPageSize()),
                    allMedicines.size(),
                    medicineResponses
            );

            return new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Medicines retrieved successfully",
                    null,
                    paginatedData
            );
        } catch (Exception e) {
            return new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error performing medicine search: " + e.getMessage(),
                    null,
                    null
            );
        }
    }


    @Override
    public ApiResponse<PaginatedResponse<RoomAllotmentResponse>> searchRoomAllotments(String query, Pageable pageable) {
        try {
            // Convert query to RoomType enum if possible
            RoomType roomType = null;
            try {
                roomType = query != null ? RoomType.valueOf(query.toUpperCase()) : null;
            } catch (IllegalArgumentException e) {
                // If query doesn't match any RoomType enum, proceed with roomNo search only
            }

            List<RoomAllotment> roomResults = roomType != null ?
                    roomAllotmentRepository.findByRoomNoContainingIgnoringCaseOrRoomType(query, roomType) :
                    roomAllotmentRepository.findByRoomNoContainingIgnoringCaseOrRoomType(query, null);

            List<RoomAllotment> patientResults = roomAllotmentRepository.findByPatientFirstNameContainingIgnoringCaseOrPatientLastNameContainingIgnoringCase(query, query);

            List<RoomAllotment> allRoomAllotments = new ArrayList<>();
            allRoomAllotments.addAll(roomResults);
            allRoomAllotments.addAll(patientResults);
            allRoomAllotments = allRoomAllotments.stream().distinct().collect(Collectors.toList());

            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), allRoomAllotments.size());
            List<RoomAllotment> pagedRoomAllotments = allRoomAllotments.subList(start, end);

            List<RoomAllotmentResponse> roomAllotmentResponses = pagedRoomAllotments.stream()
                    .map(roomAllotment -> {
                        UserResponse patientResponse = null;
                        if (roomAllotment.getPatient() != null) {
                            User patient = roomAllotment.getPatient();
                            AddressResponse patientAddress = patient.getAddress() != null ? new AddressResponse(
                                    patient.getAddress().getStreetAddress(),
                                    patient.getAddress().getCity(),
                                    patient.getAddress().getState(),
                                    patient.getAddress().getCountry()
                            ) : null;
                            patientResponse = new UserResponse(
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
                                    patient.getLastLogin() != null ? patient.getLastLogin().toString() : null,
                                    patient.getLoginDuration(),
                                    patient.getSpeciality(),
                                    patient.getAbout(),
                                    patient.getCreatedAt()
                            );
                        }
                        List<PaymentsResponse> paymentsResponses = roomAllotment.getPayments() != null ?
                                roomAllotment.getPayments().stream()
                                        .map(payment -> {
                                            UserResponse paymentPatientResponse = null;
                                            if (payment.getPatient() != null) {
                                                User pPatient = payment.getPatient();
                                                AddressResponse pPatientAddress = pPatient.getAddress() != null ? new AddressResponse(
                                                        pPatient.getAddress().getStreetAddress(),
                                                        pPatient.getAddress().getCity(),
                                                        pPatient.getAddress().getState(),
                                                        pPatient.getAddress().getCountry()
                                                ) : null;
                                                paymentPatientResponse = new UserResponse(
                                                        pPatient.getId(),
                                                        pPatient.getFirstName(),
                                                        pPatient.getLastName(),
                                                        pPatient.getMembershipNo(),
                                                        pPatient.getEmail(),
                                                        pPatient.getPhoneNumber(),
                                                        pPatient.getGender(),
                                                        pPatient.getStatus(),
                                                        pPatient.getProfilePicture(),
                                                        pPatient.getDesignation(),
                                                        pPatient.getDepartment(),
                                                        pPatient.getMaritalStatus(),
                                                        pPatient.getBloodGroup(),
                                                        pPatient.getBloodPressure(),
                                                        pPatient.getSugar(),
                                                        pPatient.getInjuryCondition(),
                                                        pPatient.getAllergies(),
                                                        pPatientAddress,
                                                        pPatient.getUserRole().name(),
                                                        pPatient.getLastLogin() != null ? pPatient.getLastLogin().toString() : null,
                                                        pPatient.getLoginDuration(),
                                                        pPatient.getSpeciality(),
                                                        pPatient.getAbout(),
                                                        pPatient.getCreatedAt()
                                                );
                                            }
                                            UserResponse paymentDoctorResponse = null;
                                            if (payment.getDoctor() != null) {
                                                User pDoctor = payment.getDoctor();
                                                AddressResponse pDoctorAddress = pDoctor.getAddress() != null ? new AddressResponse(
                                                        pDoctor.getAddress().getStreetAddress(),
                                                        pDoctor.getAddress().getCity(),
                                                        pDoctor.getAddress().getState(),
                                                        pDoctor.getAddress().getCountry()
                                                ) : null;
                                                paymentDoctorResponse = new UserResponse(
                                                        pDoctor.getId(),
                                                        pDoctor.getFirstName(),
                                                        pDoctor.getLastName(),
                                                        pDoctor.getMembershipNo(),
                                                        pDoctor.getEmail(),
                                                        pDoctor.getPhoneNumber(),
                                                        pDoctor.getGender(),
                                                        pDoctor.getStatus(),
                                                        pDoctor.getProfilePicture(),
                                                        pDoctor.getDesignation(),
                                                        pDoctor.getDepartment(),
                                                        pDoctor.getMaritalStatus(),
                                                        pDoctor.getBloodGroup(),
                                                        pDoctor.getBloodPressure(),
                                                        pDoctor.getSugar(),
                                                        pDoctor.getInjuryCondition(),
                                                        pDoctor.getAllergies(),
                                                        pDoctorAddress,
                                                        pDoctor.getUserRole().name(),
                                                        pDoctor.getLastLogin() != null ? pDoctor.getLastLogin().toString() : null,
                                                        pDoctor.getLoginDuration(),
                                                        pDoctor.getSpeciality(),
                                                        pDoctor.getAbout(),
                                                        pDoctor.getCreatedAt()
                                                );
                                            }
                                            RoomAllotmentResponse paymentRoomAllotmentResponse = null; // Avoid circular reference
                                            List<PaymentItemResponse> paymentItemResponses = payment.getPaymentItems() != null ?
                                                    payment.getPaymentItems().stream()
                                                            .map(item -> new PaymentItemResponse(
                                                                    item.getId(),
                                                                    item.getItemName(),
                                                                    item.getQuantity(),
                                                                    item.getUnitCost(),
                                                                    item.getCharges(),
                                                                    item.getTotal(),
                                                                    item.getCreatedAt(),
                                                                    item.getUpdatedAt()
                                                            ))
                                                            .collect(Collectors.toList()) : null;
                                            return new PaymentsResponse(
                                                    payment.getId(),
                                                    payment.getBillNumber(),
                                                    payment.getInvoiceNumber(),
                                                    paymentPatientResponse,
                                                    paymentDoctorResponse,
                                                    paymentRoomAllotmentResponse,
                                                    payment.getInvoiceDate(),
                                                    payment.getDueDate(),
                                                    payment.getPaymentDate(),
                                                    payment.getPaymentMethod(),
                                                    payment.getPaymentStatus(),
                                                    payment.getSubTotalAmount(),
                                                    payment.getTax(),
                                                    payment.getTotalAmount(),
                                                    payment.getNotes(),
                                                    payment.getTermsAndConditions(),
                                                    paymentItemResponses,
                                                    payment.getCreatedAt(),
                                                    payment.getUpdatedAt()
                                            );
                                        })
                                        .collect(Collectors.toList()) : null;
                        return new RoomAllotmentResponse(
                                roomAllotment.getId(),
                                roomAllotment.getRoomNo(),
                                roomAllotment.getRoomType(),
                                patientResponse,
                                roomAllotment.getAllotmentDate(),
                                roomAllotment.getDischargeDate(),
                                paymentsResponses,
                                roomAllotment.getCreatedAt(),
                                roomAllotment.getUpdatedAt()
                        );
                    })
                    .collect(Collectors.toList());

            PaginatedResponse<RoomAllotmentResponse> paginatedData = new PaginatedResponse<>(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    (int) Math.ceil((double) allRoomAllotments.size() / pageable.getPageSize()),
                    allRoomAllotments.size(),
                    roomAllotmentResponses
            );

            return new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Room allotments retrieved successfully",
                    null,
                    paginatedData
            );
        } catch (Exception e) {
            return new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error performing room allotment search: " + e.getMessage(),
                    null,
                    null
            );
        }
    }

    @Override
    public ApiResponse<PaginatedResponse<PaymentsResponse>> searchPayments(String query, Pageable pageable) {
        try {
                // Convert query to RoomType enum if possible
            RoomType roomType = null;
            try {
                roomType = query != null ? RoomType.valueOf(query.toUpperCase()) : null;
            } catch (IllegalArgumentException e) {
                    // If query doesn't match any RoomType enum, proceed with roomNo search only
            }
            List<Payments> billResults = paymentsRepository.findByBillNumberContainingIgnoringCaseOrInvoiceNumberContainingIgnoringCase(query, query);
            List<Payments> patientResults = paymentsRepository.findByPatientFirstNameContainingIgnoringCaseOrPatientLastNameContainingIgnoringCase(query, query);
            List<Payments> doctorResults = paymentsRepository.findByDoctorFirstNameContainingIgnoringCaseOrDoctorLastNameContainingIgnoringCase(query, query);
            List<Payments> roomResults = roomType != null ?
                    paymentsRepository.findByRoomAllotmentRoomNoContainingIgnoringCaseOrRoomAllotmentRoomType(query, roomType) :
                    paymentsRepository.findByRoomAllotmentRoomNoContainingIgnoringCaseOrRoomAllotmentRoomType(query, null);
            List<Payments> paymentDetailsResults = paymentsRepository.findByPaymentDetailsContainingIgnoringCase(query);

            List<Payments> allPayments = new ArrayList<>();
            allPayments.addAll(billResults);
            allPayments.addAll(patientResults);
            allPayments.addAll(doctorResults);
            allPayments.addAll(roomResults);
            allPayments.addAll(paymentDetailsResults);
            allPayments = allPayments.stream().distinct().collect(Collectors.toList());

            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), allPayments.size());
            List<Payments> pagedPayments = allPayments.subList(start, end);

            List<PaymentsResponse> paymentsResponses = pagedPayments.stream()
                    .map(payment -> {
                        UserResponse patientResponse = null;
                        if (payment.getPatient() != null) {
                            User patient = payment.getPatient();
                            AddressResponse patientAddress = patient.getAddress() != null ? new AddressResponse(
                                    patient.getAddress().getStreetAddress(),
                                    patient.getAddress().getCity(),
                                    patient.getAddress().getState(),
                                    patient.getAddress().getCountry()
                            ) : null;
                            patientResponse = new UserResponse(
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
                                    patient.getLastLogin() != null ? patient.getLastLogin().toString() : null,
                                    patient.getLoginDuration(),
                                    patient.getSpeciality(),
                                    patient.getAbout(),
                                    patient.getCreatedAt()
                            );
                        }
                        UserResponse doctorResponse = null;
                        if (payment.getDoctor() != null) {
                            User doctor = payment.getDoctor();
                            AddressResponse doctorAddress = doctor.getAddress() != null ? new AddressResponse(
                                    doctor.getAddress().getStreetAddress(),
                                    doctor.getAddress().getCity(),
                                    doctor.getAddress().getState(),
                                    doctor.getAddress().getCountry()
                            ) : null;
                            doctorResponse = new UserResponse(
                                    doctor.getId(),
                                    doctor.getFirstName(),
                                    doctor.getLastName(),
                                    doctor.getMembershipNo(),
                                    doctor.getEmail(),
                                    doctor.getPhoneNumber(),
                                    doctor.getGender(),
                                    doctor.getStatus(),
                                    doctor.getProfilePicture(),
                                    doctor.getDesignation(),
                                    doctor.getDepartment(),
                                    doctor.getMaritalStatus(),
                                    doctor.getBloodGroup(),
                                    doctor.getBloodPressure(),
                                    doctor.getSugar(),
                                    doctor.getInjuryCondition(),
                                    doctor.getAllergies(),
                                    doctorAddress,
                                    doctor.getUserRole().name(),
                                    doctor.getLastLogin() != null ? doctor.getLastLogin().toString() : null,
                                    doctor.getLoginDuration(),
                                    doctor.getSpeciality(),
                                    doctor.getAbout(),
                                    doctor.getCreatedAt()
                            );
                        }
                        RoomAllotmentResponse roomAllotmentResponse = null; // Avoid circular reference
                        List<PaymentItemResponse> paymentItemResponses = payment.getPaymentItems() != null ?
                                payment.getPaymentItems().stream()
                                        .map(item -> new PaymentItemResponse(
                                                item.getId(),
                                                item.getItemName(),
                                                item.getQuantity(),
                                                item.getUnitCost(),
                                                item.getCharges(),
                                                item.getTotal(),
                                                item.getCreatedAt(),
                                                item.getUpdatedAt()
                                        ))
                                        .collect(Collectors.toList()) : null;
                        return new PaymentsResponse(
                                payment.getId(),
                                payment.getBillNumber(),
                                payment.getInvoiceNumber(),
                                patientResponse,
                                doctorResponse,
                                roomAllotmentResponse,
                                payment.getInvoiceDate(),
                                payment.getDueDate(),
                                payment.getPaymentDate(),
                                payment.getPaymentMethod(),
                                payment.getPaymentStatus(),
                                payment.getSubTotalAmount(),
                                payment.getTax(),
                                payment.getTotalAmount(),
                                payment.getNotes(),
                                payment.getTermsAndConditions(),
                                paymentItemResponses,
                                payment.getCreatedAt(),
                                payment.getUpdatedAt()
                        );
                    })
                    .collect(Collectors.toList());

            PaginatedResponse<PaymentsResponse> paginatedData = new PaginatedResponse<>(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    (int) Math.ceil((double) allPayments.size() / pageable.getPageSize()),
                    allPayments.size(),
                    paymentsResponses
            );

            return new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Payments retrieved successfully",
                    null,
                    paginatedData
            );
        } catch (Exception e) {
            return new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error performing payment search: " + e.getMessage(),
                    null,
                    null
            );
        }
    }
}


//
//    public ApiResponse<PaginatedResponse<UserResponse>> searchUsers(String role, String query, Pageable pageable) {
//        try {
//            SearchRequest searchRequest = new SearchRequest.Builder()
//                    .index("users")
//                    .from((int) pageable.getOffset())
//                    .size(pageable.getPageSize())
//                    .query(buildQuery(role, query, List.of("firstName", "lastName", "email", "department", "education", "designation", "about")))
//                    .build();
//
//            SearchResponse<User> searchResponse = elasticsearchClient.search(searchRequest, User.class);
//
//            List<UserResponse> userResponses = searchResponse.hits().hits().stream()
//                    .map(hit -> {
//                        User user = hit.source();
//                        AddressResponse addressResponse = null;
//                        assert user != null;
//                        if (user.getAddress() != null) {
//                            addressResponse = new AddressResponse(
//                                    user.getAddress().getStreetAddress(),
//                                    user.getAddress().getCity(),
//                                    user.getAddress().getState(),
//                                    user.getAddress().getCountry()
//                            );
//                        }
//
//                        return new UserResponse(
//                                user.getId(),
//                                user.getFirstName(),
//                                user.getLastName(),
//                                user.getMembershipNo(),
//                                user.getEmail(),
//                                user.getPhoneNumber(),
//                                user.getGender(),
//                                user.getStatus(),
//                                user.getProfilePicture(),
//                                user.getEducation(),
//                                user.getDesignation(),
//                                user.getDepartment(),
//                                user.getMaritalStatus(),
//                                user.getBloodGroup(),
//                                user.getBloodPressure(),
//                                user.getSugar(),
//                                user.getInjuryCondition(),
//                                user.getAllergies(),
//                                addressResponse,
//                                user.getUserRole().name(),
//                                user.getLastLogin() != null ? user.getLastLogin().toString() : null,
//                                user.getLoginDuration(),
//                                user.getSpeciality(),
//                                user.getAbout(),
//                                user.getCreatedAt()
//                        );
//                    })
//                    .collect(Collectors.toList());
//
//            PaginatedResponse<UserResponse> paginatedData = new PaginatedResponse<>(
//                    pageable.getPageNumber(),
//                    pageable.getPageSize(),
//                    (int) Math.ceil((double) searchResponse.hits().total().value() / pageable.getPageSize()),
//                    searchResponse.hits().total().value(),
//                    userResponses
//            );
//
//            return new ApiResponse<>(
//                    HttpStatus.OK.value(),
//                    "Users retrieved successfully",
//                    null,
//                    paginatedData
//            );
//        } catch (IOException e) {
//            return new ApiResponse<>(
//                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                    "Error performing user search: " + e.getMessage(),
//                    null,
//                    null
//            );
//        }
//    }
//
//
//    public ApiResponse<PrescriptionResponse> searchPrescriptions(String query) {
//        try {
//            SearchRequest searchRequest = new SearchRequest.Builder()
//                    .index("prescriptions")
//                    .size(1) // Limit to 1 result
//                    .query(buildQuery(null, query, List.of("medication", "dosage", "instructions", "appointment.doctorName", "appointment.patientName")))
//                    .build();
//
//            SearchResponse<Prescription> searchResponse = elasticsearchClient.search(searchRequest, Prescription.class);
//
//            if (searchResponse.hits().hits().isEmpty()) {
//                return new ApiResponse<>(
//                        HttpStatus.NOT_FOUND.value(),
//                        "No prescription found for query: " + query,
//                        null,
//                        null
//                );
//            }
//
//            Prescription prescription = searchResponse.hits().hits().get(0).source();
//            AppointmentResponse appointmentResponse = null;
//            assert prescription != null;
//            if (prescription.getAppointment() != null) {
//                appointmentResponse = new AppointmentResponse(
//                        prescription.getAppointment().getId(),
//                        prescription.getAppointment().getDoctor().getId(),
//                        prescription.getAppointment().getDoctor().getFirstName() + " " + prescription.getAppointment().getDoctor().getLastName(),
//                        prescription.getAppointment().getPatient().getId(),
//                        prescription.getAppointment().getPatient().getFirstName() + " " + prescription.getAppointment().getDoctor().getLastName(),
//                        prescription.getAppointment().getDoctor().getDepartment(),
//                        prescription.getAppointment().getAppointmentDate(),
//                        prescription.getAppointment().getReason(),
//                        prescription.getAppointment().getStatus(),
//                        prescription.getAppointment().getNotes(),
//                        prescription.getAppointment().getCreatedAt()
//                );
//            }
//
//            PrescriptionResponse prescriptionResponse = new PrescriptionResponse(
//                    prescription.getId(),
//                    prescription.getMedication(),
//                    prescription.getDosage(),
//                    prescription.getInstructions(),
//                    appointmentResponse,
//                    prescription.getCreatedAt(),
//                    prescription.getUpdatedAt()
//            );
//
//            return new ApiResponse<>(
//                    HttpStatus.OK.value(),
//                    "Prescription retrieved successfully",
//                    null,
//                    prescriptionResponse
//            );
//        } catch (IOException e) {
//            return new ApiResponse<>(
//                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                    "Error performing prescription search: " + e.getMessage(),
//                    null,
//                    null
//            );
//        }
//    }
//
//
//    public ApiResponse<PaginatedResponse<AppointmentResponse>> searchAppointments(String query, Pageable pageable) {
//        try {
//            SearchRequest searchRequest = new SearchRequest.Builder()
//                    .index("appointments")
//                    .from((int) pageable.getOffset())
//                    .size(pageable.getPageSize())
//                    .query(buildQuery(null, query, List.of("doctorName", "patientName", "department", "purpose", "status", "notes")))
//                    .build();
//
//            SearchResponse<Appointment> searchResponse = elasticsearchClient.search(searchRequest, Appointment.class);
//
//            List<AppointmentResponse> appointmentResponses = searchResponse.hits().hits().stream()
//                    .map(hit -> {
//                        Appointment appointment = hit.source();
//                        assert appointment != null;
//                        return new AppointmentResponse(
//                                appointment.getId(),
//                                appointment.getDoctor().getId(),
//                                appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName(),
//                                appointment.getPatient().getId(),
//                                appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName(),
//                                appointment.getDoctor().getDepartment(),
//                                appointment.getAppointmentDate(),
//                                appointment.getReason(),
//                                appointment.getStatus(),
//                                appointment.getNotes(),
//                                appointment.getCreatedAt()
//                        );
//                    })
//                    .collect(Collectors.toList());
//
//            PaginatedResponse<AppointmentResponse> paginatedData = new PaginatedResponse<>(
//                    pageable.getPageNumber(),
//                    pageable.getPageSize(),
//                    (int) Math.ceil((double) searchResponse.hits().total().value() / pageable.getPageSize()),
//                    searchResponse.hits().total().value(),
//                    appointmentResponses
//            );
//
//            return new ApiResponse<>(
//                    HttpStatus.OK.value(),
//                    "Appointments retrieved successfully",
//                    null,
//                    paginatedData
//            );
//        } catch (IOException e) {
//            return new ApiResponse<>(
//                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                    "Error performing appointment search: " + e.getMessage(),
//                    null,
//                    null
//            );
//        }
//    }
//
//    private Query buildQuery(String role, String query, List<String> fields) {
//        BoolQuery.Builder boolQuery = new BoolQuery.Builder();
//
//        if (role != null && !role.isEmpty()) {
//            boolQuery.filter(new TermQuery.Builder()
//                    .field("userRole")
//                    .value(FieldValue.of(role.toUpperCase()))
//                    .build());
//        }
//
//        if (query != null && !query.isEmpty()) {
//            boolQuery.must(new MultiMatchQuery.Builder()
//                    .query(query)
//                    .fields(fields)
//                    .build());
//        }
//
//        return boolQuery.build()._toQuery() != null ? boolQuery.build()._toQuery() : Query.of(q -> q.matchAll(new MatchAllQuery.Builder().build()));
//    }

