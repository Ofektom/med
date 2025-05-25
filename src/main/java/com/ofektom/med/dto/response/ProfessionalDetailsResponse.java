package com.ofektom.med.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProfessionalDetailsResponse {
    private Long id;
    private Long userId;
    private List<EducationResponse> educations;
    private List<ExperienceResponse> experiences;
    private List<String> conferences;

    public ProfessionalDetailsResponse(Long id, Long userId, List<EducationResponse> educations, List<ExperienceResponse> experiences, List<String> conferences) {
        this.id = id;
        this.userId = userId;
        this.educations = educations;
        this.experiences = experiences;
        this.conferences = conferences;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setEducations(List<EducationResponse> educations) {
        this.educations = educations;
    }

    public void setExperiences(List<ExperienceResponse> experiences) {
        this.experiences = experiences;
    }

    public void setConferences(List<String> conferences) {
        this.conferences = conferences;
    }

    // Getters
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public List<EducationResponse> getEducations() { return educations; }
    @JsonProperty("experiences") // Ensure the JSON key is lowercase
    public List<ExperienceResponse> getExperiences() { return experiences; }
    public List<String> getConferences() { return conferences; }
}