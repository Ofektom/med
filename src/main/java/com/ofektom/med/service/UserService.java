package com.ofektom.med.service;

import com.ofektom.med.dto.request.AddPatientAndStaffDto;
import com.ofektom.med.dto.request.LoginDto;
import com.ofektom.med.dto.request.SignupDto;
import com.ofektom.med.dto.response.ApiResponse;
import com.ofektom.med.dto.response.UserResponse;
import com.ofektom.med.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<?> registerUser(SignupDto signupDto);

    ResponseEntity<?> loginUser(LoginDto loginDto);

    ResponseEntity<?> getUserById(Long userId);

    List<UserResponse> getAllUsersByRoleAsList(String role); // fetch all as list

    Page<UserResponse> getAllUsersByRoleAsPage(String role, Pageable pageable);
//    void createPasswordResetTokenForUser(User user, String token);
//    void forgotPassword(EmailSenderDto passwordDto, HttpServletRequest request);
//    ResponseEntity<String> resetPassword(String token, ResetPasswordDto passwordDto);
//    User saveUser(SignupDto signupDto);
//    String changeUserPassword(ChangePasswordDto passwordDto);
//    void saveVerificationTokenForUser(User user, String token);
//    User editUser(UserDto userEditDto, Long userId);
//    Optional<User> findUserById(Long userId);


    String logoutUser(HttpServletRequest request);

    ResponseEntity<?> addStaffAndPatient(AddPatientAndStaffDto addPatientAndStaffDto);

    ResponseEntity<?> validateToken(HttpServletRequest request);
}
