package com.ofektom.med.dto.response;

import com.ofektom.med.enums.RoomType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class RoomAllotmentResponse {
    private Long id;
    private String roomNo;
    private RoomType roomType;
    private UserResponse patient;
    private LocalDate allotmentDate;
    private LocalDate dischargeDate;
    private List<PaymentsResponse> payments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RoomAllotmentResponse() {}

    public RoomAllotmentResponse(Long id, String roomNo, RoomType roomType, UserResponse patient,
                                 LocalDate allotmentDate, LocalDate dischargeDate, List<PaymentsResponse> payments,
                                 LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.patient = patient;
        this.allotmentDate = allotmentDate;
        this.dischargeDate = dischargeDate;
        this.payments = payments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRoomNo() { return roomNo; }
    public void setRoomNo(String roomNo) { this.roomNo = roomNo; }
    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }
    public UserResponse getPatient() { return patient; }
    public void setPatient(UserResponse patient) { this.patient = patient; }
    public LocalDate getAllotmentDate() { return allotmentDate; }
    public void setAllotmentDate(LocalDate allotmentDate) { this.allotmentDate = allotmentDate; }
    public LocalDate getDischargeDate() { return dischargeDate; }
    public void setDischargeDate(LocalDate dischargeDate) { this.dischargeDate = dischargeDate; }
    public List<PaymentsResponse> getPayments() { return payments; }
    public void setPayments(List<PaymentsResponse> payments) { this.payments = payments; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}