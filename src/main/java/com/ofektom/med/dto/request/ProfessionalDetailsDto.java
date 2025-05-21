package com.ofektom.med.dto.request;

import com.ofektom.med.model.Education;
import com.ofektom.med.model.Experience;

import java.util.List;

public class ProfessionalDetailsDto {
    private Long id;
    private Long userId;
    private List<Education> educations;
    private List<Experience> experiences;
    private List<String> conferences;

    public ProfessionalDetailsDto() {}

    public ProfessionalDetailsDto(Long id, Long userId, List<Education> educations, List<Experience> experiences, List<String> conferences) {
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

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public List<String> getConferences() {
        return conferences;
    }

    public void setConferences(List<String> conferences) {
        this.conferences = conferences;
    }
}
