package com.ofektom.med.model;

import com.ofektom.med.enums.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "room_allotments")
public class RoomAllotment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String roomNo;

    @Enumerated(EnumType.STRING)
    @NotNull
    private RoomType roomType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull
    private User patient;

    @NotNull
    private LocalDate allotmentDate;

    @NotNull
    private LocalDate dischargeDate;

    @OneToMany(mappedBy = "roomAllotment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payments> payments;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public RoomAllotment(){}

    public RoomAllotment(Long id, String roomNo, RoomType roomType, User patient, LocalDate allotmentDate, LocalDate dischargeDate, List<Payments> payments, LocalDateTime createdAt, LocalDateTime updatedAt) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public LocalDate getAllotmentDate() {
        return allotmentDate;
    }

    public void setAllotmentDate(LocalDate allotmentDate) {
        this.allotmentDate = allotmentDate;
    }

    public  LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(LocalDate dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public List<Payments> getPayments() {
        return payments;
    }

    public void setPayments(List<Payments> payments) {
        this.payments = payments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
