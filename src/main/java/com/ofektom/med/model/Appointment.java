package com.ofektom.med.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime appointmentDate;

    @NotBlank
    private String reason;

    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull
    private User doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull
    private User patient;

    @Column(name = "\"from\"")
    private LocalDateTime from;

    @Column(name = "\"to\"")
    private LocalDateTime to;

    @NotBlank
    private String treatment;

    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Appointment() {
    }

    public Appointment(Long id, LocalDateTime appointmentDate, String reason, String status, User doctor, User patient, LocalDateTime from, LocalDateTime to, String treatment, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.reason = reason;
        this.status = status;
        this.doctor = doctor;
        this.patient = patient;
        this.from = from;
        this.to = to;
        this.treatment = treatment;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(@NotNull LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public @NotBlank String getReason() {
        return reason;
    }

    public void setReason(@NotBlank String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public @NotNull User getDoctor() {
        return doctor;
    }

    public void setDoctor(@NotNull User doctor) {
        this.doctor = doctor;
    }

    public @NotNull User getPatient() {
        return patient;
    }

    public void setPatient(@NotNull User patient) {
        this.patient = patient;
    }

    public @NotNull LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(@NotNull LocalDateTime from) {
        this.from = from;
    }

    public @NotNull LocalDateTime getTo() {
        return to;
    }

    public void setTo(@NotNull LocalDateTime to) {
        this.to = to;
    }

    public @NotBlank String getTreatment() {
        return treatment;
    }

    public void setTreatment(@NotBlank String treatment) {
        this.treatment = treatment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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