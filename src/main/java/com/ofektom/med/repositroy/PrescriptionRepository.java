package com.ofektom.med.repositroy;

import com.ofektom.med.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    Optional<Prescription> findFirstByMedicationContainingIgnoringCaseOrDosageContainingIgnoringCaseOrInstructionsContainingIgnoringCase(
            String medication, String dosage, String instructions);

    Optional<Prescription> findFirstByAppointmentDoctorFirstNameContainingIgnoringCaseOrAppointmentDoctorLastNameContainingIgnoringCase(
            String doctorFirstName, String doctorLastName);

    Optional<Prescription> findFirstByAppointmentPatientFirstNameContainingIgnoringCaseOrAppointmentPatientLastNameContainingIgnoringCase(
            String patientFirstName, String patientLastName);
}
