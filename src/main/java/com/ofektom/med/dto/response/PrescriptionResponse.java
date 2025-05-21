package com.ofektom.med.dto.response;

import java.time.LocalDateTime;

public class PrescriptionResponse {
    private Long id;
    private String medication;
    private String dosage;
    private String instructions;
    private AppointmentResponse appointment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PrescriptionResponse() {}

    public PrescriptionResponse(Long id, String medication, String dosage, String instructions,
                                AppointmentResponse appointment, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.medication = medication;
        this.dosage = dosage;
        this.instructions = instructions;
        this.appointment = appointment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMedication() { return medication; }
    public void setMedication(String medication) { this.medication = medication; }
    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public AppointmentResponse getAppointment() { return appointment; }
    public void setAppointment(AppointmentResponse appointment) { this.appointment = appointment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}