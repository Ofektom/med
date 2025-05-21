package com.ofektom.med.dto.response;

import java.util.List;

public class ProfessionalDetailsResponse {
    private Long id;
    private Long userId;
    private List<EducationResponse> educations;
    private List<ExperienceResponse> experiences;
    private List<String> conferences;

    public ProfessionalDetailsResponse() {}

    public ProfessionalDetailsResponse(Long id, Long userId, List<EducationResponse> educations, List<ExperienceResponse> experiences, List<String> conferences) {
        this.id = id;
        this.userId = userId;
        this.educations = educations;
        this.experiences = experiences;
        this.conferences = conferences;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<EducationResponse> getEducations() {
        return educations;
    }

    public void setEducations(List<EducationResponse> educations) {
        this.educations = educations;
    }

    public List<ExperienceResponse> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<ExperienceResponse> experiences) {
        this.experiences = experiences;
    }

    public List<String> getConferences() {
        return conferences;
    }

    public void setConferences(List<String> conferences) {
        this.conferences = conferences;
    }
}
