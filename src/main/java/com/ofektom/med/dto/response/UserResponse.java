package com.ofektom.med.dto.response;

import java.time.LocalDateTime;
import java.util.List;


public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String membershipNo;
    private String email;
    private String phoneNumber;
    private String gender;
    private String status;
    private String profilePicture;
    private String designation;
    private String department;
    private String maritalStatus;
    private String bloodGroup;
    private String bloodPressure;
    private String sugar;
    private String injuryCondition;
    private List<String> allergies;
    private AddressResponse address;
    private String userRole;
    private String lastLogin;
    private String loginDuration;
    private String speciality;
    private String about;
    private LocalDateTime createdAt;

    public UserResponse(){}

    public UserResponse(Long id, String firstName, String lastName, String membershipNo, String email, String phoneNumber, String gender, String status, String profilePicture, String designation, String department, String maritalStatus, String bloodGroup, String bloodPressure, String sugar, String injuryCondition, List<String> allergies, AddressResponse address, String userRole, String lastLogin, String loginDuration, String speciality, String about, LocalDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.membershipNo = membershipNo;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.status = status;
        this.profilePicture = profilePicture;
        this.designation = designation;
        this.department = department;
        this.maritalStatus = maritalStatus;
        this.bloodGroup = bloodGroup;
        this.bloodPressure = bloodPressure;
        this.sugar = sugar;
        this.injuryCondition = injuryCondition;
        this.allergies = allergies;
        this.address = address;
        this.userRole = userRole;
        this.lastLogin = lastLogin;
        this.loginDuration = loginDuration;
        this.speciality = speciality;
        this.about = about;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMembershipNo() {
        return membershipNo;
    }

    public void setUsername(String username) {
        this.membershipNo = membershipNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
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

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
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

    public AddressResponse getAddress() {
        return address;
    }

    public void setAddress(AddressResponse address) {
        this.address = address;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLoginDuration() {
        return loginDuration;
    }

    public void setLoginDuration(String loginDuration) {
        this.loginDuration = loginDuration;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}