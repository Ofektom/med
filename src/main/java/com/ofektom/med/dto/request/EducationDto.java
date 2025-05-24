package com.ofektom.med.dto.request;

public class EducationDto {
    private Long id;
    private String year;
    private String degree;
    private String institute;
    private String result;

    public EducationDto(){}

    public EducationDto(Long id, String year, String degree, String institute, String result) {
        this.id = id;
        this.year = year;
        this.degree = degree;
        this.institute = institute;
        this.result = result;
    }

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

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
