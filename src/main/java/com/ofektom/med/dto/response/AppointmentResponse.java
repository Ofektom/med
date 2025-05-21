package com.ofektom.med.dto.response;

import java.time.LocalDateTime;

public class AppointmentResponse {
    private Long id;
    private LocalDateTime appointmentDate;
    private String reason;
    private String status;
    private UserResponse doctor;
    private UserResponse patient;
    private LocalDateTime from;
    private LocalDateTime to;
    private String treatment;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AppointmentResponse() {}

    public AppointmentResponse(Long id, LocalDateTime appointmentDate, String reason, String status,
                               UserResponse doctor, UserResponse patient, LocalDateTime from, LocalDateTime to,
                               String treatment, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
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

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public UserResponse getDoctor() { return doctor; }
    public void setDoctor(UserResponse doctor) { this.doctor = doctor; }
    public UserResponse getPatient() { return patient; }
    public void setPatient(UserResponse patient) { this.patient = patient; }
    public LocalDateTime getFrom() { return from; }
    public void setFrom(LocalDateTime from) { this.from = from; }
    public LocalDateTime getTo() { return to; }
    public void setTo(LocalDateTime to) { this.to = to; }
    public String getTreatment() { return treatment; }
    public void setTreatment(String treatment) { this.treatment = treatment; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}