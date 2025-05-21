package com.ofektom.med.repositroy;

import com.ofektom.med.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByReasonContainingIgnoringCaseOrStatusContainingIgnoringCaseOrTreatmentContainingIgnoringCaseOrNotesContainingIgnoringCase(
            String reason, String status, String treatment, String notes);

    List<Appointment> findByDoctorFirstNameContainingIgnoringCaseOrDoctorLastNameContainingIgnoringCase(
            String doctorFirstName, String doctorLastName);

    List<Appointment> findByPatientFirstNameContainingIgnoringCaseOrPatientLastNameContainingIgnoringCase(
            String patientFirstName, String patientLastName);
}
