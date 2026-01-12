package com.ofektom.med.controller;


import com.ofektom.med.dto.request.AddPatientAndStaffDto;
import com.ofektom.med.dto.request.LoginDto;
import com.ofektom.med.dto.request.SignupDto;
import com.ofektom.med.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "https://airway-ng.netlify.app"}, allowCredentials = "true")
@RequestMapping("/api/v1/auth")
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    private final UserService userService;
    @Autowired
    public AuthController(ApplicationEventPublisher publisher, UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    // Allow registration without auth if no super admin exists (for first super admin)
    // Otherwise requires SUPER_ADMIN role
    public ResponseEntity<?> register(@Valid @RequestBody SignupDto signupDto){
        return userService.registerUser(signupDto);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<?> addPatientAndStaff(@Valid @RequestBody AddPatientAndStaffDto addPatientAndStaffDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : "anonymous";
        String authorities = auth != null ? auth.getAuthorities().toString() : "none";
        
        logger.info("POST /api/v1/auth/add - User: {}, Authorities: {}, Requested role: {}", 
                username, authorities, addPatientAndStaffDto.getUserRole());
        logger.debug("POST /api/v1/auth/add - Request data: email={}, firstName={}, lastName={}", 
                addPatientAndStaffDto.getEmail(), addPatientAndStaffDto.getFirstName(), addPatientAndStaffDto.getLastName());
        
        try {
            ResponseEntity<?> response = userService.addStaffAndPatient(addPatientAndStaffDto);
            logger.info("POST /api/v1/auth/add - Success for user: {}, added role: {}", 
                    username, addPatientAndStaffDto.getUserRole());
            return response;
        } catch (Exception e) {
            logger.error("POST /api/v1/auth/add - Error for user: {}", username, e);
            throw e;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto){
        return userService.loginUser(loginDto);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        return userService.validateToken(request);
    }



//    @GetMapping("/verifyRegistration")
//    public ResponseEntity<String> verifyRegistration(@RequestParam("token") String token){
//        String result = userService.validateVerificationToken(token);
//        if (result.equalsIgnoreCase("valid")){
//            return new ResponseEntity<>( "User Verified Successfully",HttpStatus.OK);
//        }
//        return new ResponseEntity<>("User not Verified", HttpStatus.OK);
//    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
//String result = userService.logInUser(loginDto);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }


//    @PostMapping("/changePassword")
//    public ResponseEntity <String> changePassword(@RequestBody ChangePasswordDto passwordDto) {
//        return new ResponseEntity<>(userService.changeUserPassword(passwordDto), HttpStatus.OK);
//    }
//
//    @PostMapping("/forgot-password")
//    public ResponseEntity<String> forgotPassword(@RequestBody EmailSenderDto passwordDto, HttpServletRequest request){
//        userService.forgotPassword(passwordDto, request);
//        return new ResponseEntity<>("Forgot password email successfully sent", HttpStatus.OK);
//
//    }
//
//    @PostMapping("/reset-password/{token}")
//    public ResponseEntity<String> resetPassword(@PathVariable String token, @RequestBody ResetPasswordDto passwordDto) {
//        return userService.resetPassword(token, passwordDto);
//    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request){
        String result = userService.logoutUser(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
