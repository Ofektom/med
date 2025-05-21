package com.ofektom.med.repositroy;

import com.ofektom.med.enums.RoomType;
import com.ofektom.med.model.RoomAllotment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomAllotmentRepository extends JpaRepository<RoomAllotment, Long> {

    List<RoomAllotment> findByRoomNoContainingIgnoringCaseOrRoomType(String roomNo, RoomType roomType);

    List<RoomAllotment> findByPatientFirstNameContainingIgnoringCaseOrPatientLastNameContainingIgnoringCase(
            String patientFirstName, String patientLastName);
    Optional<RoomAllotment> findByRoomNo(String roomNo);
    boolean existsByRoomNoAndPatientId(String roomNo, Long patientId);
}
