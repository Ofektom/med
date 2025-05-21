package com.ofektom.med.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ofektom.med.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Size(min = 3, message = "First name must be at least 3 characters")
    private String firstName;

    @Size(min = 3, message = "Last name must be at least 3 characters")
    private String lastName;

    @Email(message = "Please enter a valid email address")
    @Column(unique = true)
    private String email;

    private String phoneNumber;

    private String password;

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Role userRole;

    @JsonIgnore
    private Boolean isEnabled = true;

    private String designation;

    private String department;

    private String postalCode;

    private String about;

    private String status;

    private Integer age;

    private String maritalStatus;

    private String profilePicture;

    private String bloodGroup;

    private String bloodPressure;

    private String sugar;

    private String injuryCondition;

    @ElementCollection
    @CollectionTable(name = "user_allergies", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "allergy")
    private List<@NotBlank String> allergies;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tasks> tasks;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointmentsAsDoctor;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointmentsAsPatient;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoomAllotment> roomAllotments;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Report> reportsAsPatient;

    @OneToMany(mappedBy = "generatedBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Report> reportsGenerated;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ProfessionalDetails professionalDetails;

    private LocalDateTime lastLogin;

    private String loginDuration;

    private String speciality;

    @JsonIgnore
    private String gender;
    @JsonIgnore
    private String dateOfBirth;
    @JsonIgnore
    private String membershipNo;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public User(){}

    public User(String firstName, String lastName, String email, String phoneNumber, String password, Role userRole, Boolean isEnabled, String membershipNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userRole = userRole;
        this.isEnabled = isEnabled;
        this.membershipNo = membershipNo;
    }

    public User(
            String firstName, String lastName, String email, String phoneNumber,
            String password, Role userRole, Boolean isEnabled, ProfessionalDetails professionalDetails,
            String designation, String department, String postalCode, String about,
            String status, Integer age, String maritalStatus, String profilePicture,
            String bloodGroup, String bloodPressure, String sugar, String injuryCondition,
            List<@NotBlank String> allergies, Address address, List<Tasks> tasks,
            List<Appointment> appointmentsAsDoctor, List<Appointment> appointmentsAsPatient,
            List<RoomAllotment> roomAllotments, List<Report> reportsAsPatient,
            List<Report> reportsGenerated, LocalDateTime lastLogin, String loginDuration,
            String speciality, String gender, String dateOfBirth, String membershipNo,
            LocalDateTime createdAt, LocalDateTime updatedAt
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.professionalDetails = professionalDetails;
        this.password = password;
        this.userRole = userRole;
        this.isEnabled = isEnabled;
        this.designation = designation;
        this.department = department;
        this.postalCode = postalCode;
        this.about = about;
        this.status = status;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.profilePicture = profilePicture;
        this.bloodGroup = bloodGroup;
        this.bloodPressure = bloodPressure;
        this.sugar = sugar;
        this.injuryCondition = injuryCondition;
        this.allergies = allergies;
        this.address = address;
        this.tasks = tasks;
        this.appointmentsAsDoctor = appointmentsAsDoctor;
        this.appointmentsAsPatient = appointmentsAsPatient;
        this.roomAllotments = roomAllotments;
        this.reportsAsPatient = reportsAsPatient;
        this.reportsGenerated = reportsGenerated;
        this.lastLogin = lastLogin;
        this.loginDuration = loginDuration;
        this.speciality = speciality;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.membershipNo = membershipNo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(Collections.singleton(new SimpleGrantedAuthority(this.userRole.name())));
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.isEnabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public @Email(message = "Please enter a valid email address") @NotEmpty(message = "Email cannot be empty") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Please enter a valid email address") @NotEmpty(message = "Email cannot be empty") String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public List<@NotBlank String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<@NotBlank String> allergies) {
        this.allergies = allergies;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<Tasks> tasks) {
        this.tasks = tasks;
    }

    public List<Appointment> getAppointmentsAsDoctor() {
        return appointmentsAsDoctor;
    }

    public void setAppointmentsAsDoctor(List<Appointment> appointmentsAsDoctor) {
        this.appointmentsAsDoctor = appointmentsAsDoctor;
    }

    public List<Appointment> getAppointmentsAsPatient() {
        return appointmentsAsPatient;
    }

    public void setAppointmentsAsPatient(List<Appointment> appointmentsAsPatient) {
        this.appointmentsAsPatient = appointmentsAsPatient;
    }

    public List<RoomAllotment> getRoomAllotments() {
        return roomAllotments;
    }

    public void setRoomAllotments(List<RoomAllotment> roomAllotments) {
        this.roomAllotments = roomAllotments;
    }

    public List<Report> getReportsAsPatient() {
        return reportsAsPatient;
    }

    public void setReportsAsPatient(List<Report> reportsAsPatient) {
        this.reportsAsPatient = reportsAsPatient;
    }

    public List<Report> getReportsGenerated() {
        return reportsGenerated;
    }

    public void setReportsGenerated(List<Report> reportsGenerated) {
        this.reportsGenerated = reportsGenerated;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMembershipNo() {
        return membershipNo;
    }

    public void setMembershipNo(String membershipNo) {
        this.membershipNo = membershipNo;
    }

    public ProfessionalDetails getProfessionalDetails() {
        return professionalDetails;
    }

    public void setProfessionalDetails(ProfessionalDetails professionalDetails) {
        this.professionalDetails = professionalDetails;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
