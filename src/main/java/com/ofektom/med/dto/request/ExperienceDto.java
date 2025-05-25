package com.ofektom.med.dto.request;

import jakarta.validation.constraints.NotBlank;

public class ExperienceDto {
    @NotBlank(message = "Year is required")
    private String year;
    @NotBlank(message = "Department is required")
    private String department;
    @NotBlank(message = "Position is required")
    private String position;
    @NotBlank(message = "Hospital is required")
    private String hospital;
    private String feedback;

    // Getters and setters
    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getHospital() { return hospital; }
    public void setHospital(String hospital) { this.hospital = hospital; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}