package com.ofektom.med.dto.request;

import jakarta.validation.constraints.*;

import java.util.List;


public class SignupDto {
    @Size(min = 3, message = "First name must be at least 3 characters")
    private String firstName;
    @Size(min = 3, message = "Last name must be at least 3 characters")
    private String lastName;
    @Email(message = "*Entry must be an email address")
    @NotEmpty(message = "*Enter your valid email address")
    private String email;
    private String country;
    private String state;
    private String city;
    private String streetAddress;
    private String postalCode;
    @NotEmpty
    private String phoneNumber;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$", message = "*Enter at least one uppercase,lowercase,digit and special character and minimum 8 characters")
    private String password;
    private String education;

    private String designation;

    private String department;

    private String dateOfBirth;

    private String gender;

    private String about;

    private String status;

    private Integer age;

    private String userRole;

    private String maritalStatus;

    private String profilePicture;

    private String bloodGroup;

    private String bloodPressure;

    private String sugar;

    private String injuryCondition;

    private List<String> allergies;



    public SignupDto(){}

    public SignupDto(String firstName, String lastName, String email, String country, String phoneNumber, String password, String education, String designation, String department, String dateOfBirth, String gender, String about, String status, Integer age, String userRole, String maritalStatus, String profilePicture, String bloodGroup, String bloodPressure, String sugar, String injuryCondition, List<String> allergies, String state, String city, String streetAddress, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.country = country;
        this.state = state;
        this.city = city;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.education = education;
        this.designation = designation;
        this.department = department;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.about = about;
        this.status = status;
        this.age = age;
        this.userRole = userRole;
        this.maritalStatus = maritalStatus;
        this.profilePicture = profilePicture;
        this.bloodGroup = bloodGroup;
        this.bloodPressure = bloodPressure;
        this.sugar = sugar;
        this.injuryCondition = injuryCondition;
        this.allergies = allergies;
    }

    public @Size(min = 3, message = "First name must be at least 3 characters") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Size(min = 3, message = "First name must be at least 3 characters") String firstName) {
        this.firstName = firstName;
    }

    public @Size(min = 3, message = "Last name must be at least 3 characters") String getLastName() {
        return lastName;
    }

    public void setLastName(@Size(min = 3, message = "Last name must be at least 3 characters") String lastName) {
        this.lastName = lastName;
    }

    public @Email(message = "*Entry must be an email address") @NotEmpty(message = "*Enter your valid email address") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "*Entry must be an email address") @NotEmpty(message = "*Enter your valid email address") String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public @NotEmpty String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotEmpty String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$", message = "*Enter at least one uppercase,lowercase,digit and special character and minimum 8 characters") String getPassword() {
        return password;
    }

    public void setPassword(@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$", message = "*Enter at least one uppercase,lowercase,digit and special character and minimum 8 characters") String password) {
        this.password = password;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getSugar() {
        return sugar;
    }

    public void setSugar(String sugar) {
        this.sugar = sugar;
    }

    public String getInjuryCondition() {
        return injuryCondition;
    }

    public void setInjuryCondition(String injuryCondition) {
        this.injuryCondition = injuryCondition;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
}
