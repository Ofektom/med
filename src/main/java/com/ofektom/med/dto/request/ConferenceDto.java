package com.ofektom.med.dto.request;

public class ConferenceDto {
    private String conference;

    public ConferenceDto(){}

    public ConferenceDto(String conference) {
        this.conference = conference;
    }

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }
}
