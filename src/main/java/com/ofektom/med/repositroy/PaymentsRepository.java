package com.ofektom.med.repositroy;

import com.ofektom.med.enums.RoomType;
import com.ofektom.med.model.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentsRepository extends JpaRepository<Payments, Long> {

    List<Payments> findByBillNumberContainingIgnoringCaseOrInvoiceNumberContainingIgnoringCase(
            String billNumber, String invoiceNumber);

    List<Payments> findByPatientFirstNameContainingIgnoringCaseOrPatientLastNameContainingIgnoringCase(
            String patientFirstName, String patientLastName);

    List<Payments> findByDoctorFirstNameContainingIgnoringCaseOrDoctorLastNameContainingIgnoringCase(
            String doctorFirstName, String doctorLastName);

    List<Payments> findByRoomAllotmentRoomNoContainingIgnoringCaseOrRoomAllotmentRoomType(
            String roomNo, RoomType roomType);

    @Query("SELECT p FROM Payments p WHERE LOWER(p.paymentMethod) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(p.paymentStatus) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(p.notes) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(p.termsAndConditions) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Payments> findByPaymentDetailsContainingIgnoringCase(@Param("query") String query);
}
