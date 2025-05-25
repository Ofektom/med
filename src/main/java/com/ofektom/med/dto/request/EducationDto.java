package com.ofektom.med.dto.request;

import jakarta.validation.constraints.NotBlank;

public class EducationDto {
    @NotBlank(message = "Year is required")
    private String year;
    @NotBlank(message = "Degree is required")
    private String degree;
    @NotBlank(message = "Institute is required")
    private String institute;
    private String result;

    // Getters and setters
    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }
    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }
    public String getInstitute() { return institute; }
    public void setInstitute(String institute) { this.institute = institute; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
}