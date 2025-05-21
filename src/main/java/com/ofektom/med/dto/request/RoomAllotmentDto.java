package com.ofektom.med.dto.request;

import com.ofektom.med.enums.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class RoomAllotmentDto {
    @NotBlank
    private String roomNo;

    @NotNull
    private RoomType roomType;

    @NotNull
    private Long patientId;

    @NotNull
    private LocalDate allotmentDate;

    @NotNull
    private LocalDate dischargeDate;

    public RoomAllotmentDto() {}

    public RoomAllotmentDto(String roomNo, RoomType roomType, Long patientId, LocalDate allotmentDate, LocalDate dischargeDate) {
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.patientId = patientId;
        this.allotmentDate = allotmentDate;
        this.dischargeDate = dischargeDate;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public LocalDate getAllotmentDate() {
        return allotmentDate;
    }

    public void setAllotmentDate(LocalDate allotmentDate) {
        this.allotmentDate = allotmentDate;
    }

    public LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(LocalDate dischargeDate) {
        this.dischargeDate = dischargeDate;
    }
}