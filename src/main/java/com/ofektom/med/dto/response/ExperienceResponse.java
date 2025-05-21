package com.ofektom.med.dto.response;

public class ExperienceResponse {
    private Long id;
    private String year;
    private String department;
    private String position;
    private String hospital;
    private String feedback;

    public ExperienceResponse() {}

    public ExperienceResponse(Long id, String year, String department, String position, String hospital, String feedback) {
        this.id = id;
        this.year = year;
        this.department = department;
        this.position = position;
        this.hospital = hospital;
        this.feedback = feedback;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
